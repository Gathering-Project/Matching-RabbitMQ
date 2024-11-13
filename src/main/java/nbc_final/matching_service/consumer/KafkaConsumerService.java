//package nbc_final.matching_service.consumer;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import nbc_final.gathering.domain.matching.dto.request.MatchingRequestDto;
//import nbc_final.matching_service.service.MatchingService;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//
//@Slf4j
//@RequiredArgsConstructor
//@Service
//public class KafkaConsumerService {
//
//    private final MatchingService matchingService;
//
//    @KafkaListener(
////            topics = "matching", groupId = "order-consumer-group1"
//            topics = "matching", groupId = "matching_group"
//    )
//    public void consumer(ConsumerRecord<String, Object> record) {
//        Object request = record.value(); // 메시지의 값만 추출
//
//        log.info("request = {}", request);
//        log.info("Listen :: order : {}", request.toString());
//        log.info("Received message of type: {}", request.getClass().getName());
//
//        if (request instanceof Long) {
//            matchingService.cancelMatching((Long) request);
//        } else if (request instanceof MatchingRequestDto) {
//            matchingService.add((MatchingRequestDto) request);
//        }
//
//        log.info("매칭 리스트 사이즈: {}", matchingService.matchingList.size());
//
//        StringBuilder userIds = new StringBuilder("유저 ID: ");
//        matchingService.matchingList.stream()
//                .filter(user -> user != null)
//                .forEach(user -> userIds.append(user.getUserId()).append(", "));
//
//        if (userIds.length() > 0) {
//            userIds.setLength(userIds.length() - 2); // 마지막 ", " 제거
//        }
//
//        log.info("매칭 대기 유저 ID 목록: {}", userIds.toString());
//    }
//
//}
