package nbc_final.matching_service.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
// RabbitMQ에 메시지를 전송할 수 있도록 도와주는 컴포넌트
public class Producer {

    private final RabbitTemplate rabbitTemplate;

    public void producer(String exchange, String routeKey, Object object) {
        rabbitTemplate.convertAndSend(exchange, routeKey, object);
    }
}