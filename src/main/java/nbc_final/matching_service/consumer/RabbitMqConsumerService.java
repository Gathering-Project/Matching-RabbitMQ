package nbc_final.matching_service.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nbc_final.matching_service.dto.MatchingRequestDto;
import nbc_final.matching_service.service.MatchingService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RabbitMqConsumerService {

    private final MatchingService matchingService;

    @RabbitListener(queues = "matching.request")
    public void matchingRequestConsumer(MatchingRequestDto matchingRequestDto) {
        log.info("유저 ID 매칭 시작: {}", matchingRequestDto.getUserId());
        matchingService.add(matchingRequestDto);
    }

    @RabbitListener(queues = "matching.cancel")
    public void matchingCancelConsumer(Long userId) {
        log.info("유저 ID 매칭 취소: {}", userId);
        matchingService.cancelMatching(userId);
    }


}
