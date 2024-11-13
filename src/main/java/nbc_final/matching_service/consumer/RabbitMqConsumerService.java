package nbc_final.matching_service.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nbc_final.gathering.domain.matching.dto.request.MatchingRequestDto;
import nbc_final.matching_service.service.MatchingService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RabbitMqConsumerService {

    private final MatchingService matchingService;

    @RabbitListener(queues = "matching.queue")
    public void matchingUserConsumer(MatchingRequestDto matchingRequestDto) {
        log.info("{}", matchingRequestDto.getUserId());
        log.info("{}", matchingRequestDto.getInterestType());
        log.info("{}", matchingRequestDto.getLocation());
        log.info("matchingUserConsumer: {}", matchingRequestDto);
        matchingService.add(matchingRequestDto);
    }
}
