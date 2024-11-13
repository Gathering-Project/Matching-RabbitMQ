package nbc_final.matching_service.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nbc_final.gathering.domain.matching.dto.request.MatchingRequestDto;
import nbc_final.gathering.domain.matching.dto.response.MatchingFailed;
import nbc_final.gathering.domain.matching.dto.response.MatchingSuccess;
import nbc_final.matching_service.entity.Matching;
import nbc_final.matching_service.enums.MatchingStatus;
import nbc_final.matching_service.producer.MatchingProducer;
import nbc_final.matching_service.repository.MatchingRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Deque;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MatchingService {

    private final RabbitTemplate rabbitTemplate;
    private final MatchingRepository matchingRepository;
    public final Deque<MatchingRequestDto> matchingList = new ConcurrentLinkedDeque<>(); // 매칭 원하는 유저들 정보가 존재하는 매칭 대기 큐
    public final Map<Long, Integer> matchingFailedMap = new ConcurrentHashMap<>(); // 해당 유저가 매칭에 몇 번째 실패했는지 기록하는 맵

    // 매칭 조건 : 유저간의 관심사와 지역이 같으면 매칭
    @Scheduled(fixedDelay = 10000, initialDelay = 1000) // 1초 후 10초마다 동작
    public void matching() {

        // 매칭 대기열에 유저가 존재하면
        while (!matchingList.isEmpty()) {

            log.info("매칭 메서드 호출: {}", LocalDateTime.now());
            log.info("매칭 대기 인원 수: {}", matchingList.size());
            if (!matchingList.isEmpty()) {
                log.info("현재 매칭 유저 ID: {}", matchingList.peek().getUserId());
            }

            StringBuilder userIds = new StringBuilder("유저 ID: "); // 현재 대기열의 유저 확인

            matchingList.stream()
                    .filter(user -> user != null)
                    .forEach(user -> userIds.append(user.getUserId()).append(", "));

            if (userIds.length() > 0) {
                userIds.setLength(userIds.length() - 2); // 마지막 ", " 제거
            }

            log.info("매칭 대기 유저 ID 목록: {}", userIds.toString()); // 현재 대기열의 유저 로깅

            MatchingRequestDto matchingUser1 = matchingList.poll(); // 가장 먼저 신청한 유저 매칭 시도
            Optional<MatchingRequestDto> otherMatchingUser = matchingList.stream()
                    .filter(waitingUser -> isPossibleMatching(matchingUser1, waitingUser)) // 관심사, 지역이 같은 유저 있는지 필터링
                    .findFirst();

            // 만약 매칭되는 유저가 존재하지 않는다면
            if (!otherMatchingUser.isPresent()) {
                matchingFailedMap.put(matchingUser1.getUserId(), matchingFailedMap.getOrDefault(matchingUser1.getUserId(), 0) + 1);
                log.info("ID {}인 유저가 조건에 부합하는 상대 유저가 없어서 매칭에 {}번째 실패하였습니다.", matchingUser1.getUserId(), matchingFailedMap.get(matchingUser1.getUserId()));
                System.out.println();

                // 매칭 시도 3번 이상 실패하면 매칭 종료
                if (matchingFailedMap.get(matchingUser1.getUserId()) >= 3) {
                    log.info("매칭에 3번째 실패하였으므로 ID {}인 유저의 매칭을 종료합니다. 잠시 후 다시 시도해주세요.", matchingUser1.getUserId());
                    System.out.println();
                    matchingFailedMap.remove(matchingUser1.getUserId()); // 해당 유저 실패 기록 초기화
                    MatchingFailed matchingFailed = new MatchingFailed(matchingUser1.getUserId());
                    sendMatchingFailed(matchingFailed); // 매칭 실패 알림 전송

                } else {
                    matchingList.offerFirst(matchingUser1); // 아직 실패 횟수 3회 미만이면 다시 매칭 대기열 맨 앞에 삽입
                }
            } else { // 매칭 성공
                MatchingRequestDto matchingUser2 = otherMatchingUser.get();
                matchingList.remove(matchingUser2); // 다른 유저와 매칭 성공했으므로 매칭 대기열에서 유저 삭제

                // 매칭 엔티티 생성
                Matching matching = Matching.createMatching(
                        matchingUser1.getUserId(),
                        matchingUser2.getUserId(),
                        matchingUser1.getInterestType(),
                        matchingUser1.getLocation(),
                        MatchingStatus.SUCCESS
                );

                log.info("관심사 {}, 거주 지역 {}인 유저 ID {}와 유저 ID {}간의 매칭이 성립되었습니다.", matchingUser1.getInterestType(), matchingUser1.getLocation(), matchingUser1.getUserId(), matchingUser2.getUserId());
                System.out.println();
                matchingRepository.save(matching); // 매칭 DB에 저장
                MatchingSuccess matchingResponseDto = new MatchingSuccess(
                        String.valueOf(matching.getId()),
                        matching.getUserId1(),
                        matchingUser2.getUserId()
                );

                sendMatcingSucess(matchingResponseDto); // 채팅으로 유저 ID 전송
            }
        }
    }

    // 매칭 취소 메서드
    public void cancelMatching(Long userId) {
        // 매칭 대기 리스트에서 해당 유저 삭제
        boolean removed = matchingList.removeIf(user -> user.getUserId().equals(userId));
        if (removed) {
            // 매칭 실패 횟수 기록에서도 삭제
            matchingFailedMap.remove(userId);
            log.info("유저 {}의 매칭이 취소되었습니다.", userId);
        } else {
            log.info("유저 {}는 매칭 대기 리스트에 없습니다.", userId);
        }
    }

    // 매칭 큐에 유저 추가
    public void add(MatchingRequestDto requestDto) {
        validateDuplicateUser(requestDto);
        matchingList.add(requestDto);
    }

    // 매칭에 중복되는 유저 있는지(해당 유저가 이미 매칭을 시도 중인지) 확인
    public void validateDuplicateUser(MatchingRequestDto requestDto) {
        Long userId = requestDto.getUserId();
        Optional<MatchingRequestDto> existingUser = matchingList.stream()
                .filter(user -> user.getUserId().equals(userId))
                .findAny();

        if (existingUser.isPresent()) {
            matchingList.remove(existingUser);
            throw new IllegalArgumentException("이미 매칭 요청되었습니다.");
        }
    }

    // 매칭 성공 전송
    private void sendMatcingSucess(MatchingSuccess matchingSuccess) {
        log.info("ID {} 유저와 ID {} 유저간 매칭 성공", matchingSuccess.getUserId1(), matchingSuccess.getUserId2());
        rabbitTemplate.convertAndSend("matching.exchange", "chatting.message.routingKey", matchingSuccess);

    }

    // 매칭 실패 전송
    private void sendMatchingFailed(MatchingFailed matchingFailed) {
        log.info("ID {} 유저 매칭 실패", matchingFailed.getFailedUserId());
        rabbitTemplate.convertAndSend("matching.exchange", "chatting.message.routingKey", matchingFailed);
    }

    // 매칭 가능 여부
    private boolean isPossibleMatching(MatchingRequestDto matchingUser1, MatchingRequestDto waitingUser) {
        // 유저끼리 관심사랑 지역 같으면 매칭 가능
        return matchingUser1.getInterestType() == waitingUser.getInterestType() && matchingUser1.getLocation().equals(waitingUser.getLocation());
    }

}
