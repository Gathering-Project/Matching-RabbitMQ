package nbc_final.matching_service.producer;

import lombok.RequiredArgsConstructor;
import nbc_final.gathering.domain.matching.dto.response.MatchingSuccess;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MatchingProducer {

    private final RabbitTemplate rabbitTemplate;

    private static final String EXCHANGE = "matching.exchange";
    private static final String ROUTE_KEY = "matching.key";

    // RabbitMQ에 매칭 성공 유저들 ID 전송
    public void sendSuccess(MatchingSuccess matchingUserDto) {
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTE_KEY, matchingUserDto);

    }

    // RabbitMQ에 매칭 실패 유저 ID 전송
    public void sendFailed(Long failedUserId) {
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTE_KEY, failedUserId);
    }
}
