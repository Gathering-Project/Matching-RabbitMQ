package nbc_final.matching_service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

//    @Bean
//    public DirectExchange directExchange() {
//        return new DirectExchange("matching.exchange");
//    }
//
//    @Bean
//    public Queue queue() {
//        return new Queue("matching.queue");
//    }
//
//    @Bean
//    public Binding binding(DirectExchange directExchange, Queue queue) {
//        return BindingBuilder.bind(queue).to(directExchange).with("matching.key");
//    }
//
//    @Bean
//    public RabbitTemplate rabbitTemplate(
//            ConnectionFactory connectionFactory,
//            MessageConverter messageConverter
//    ) {
//        var rabbitTemplate = new RabbitTemplate(connectionFactory);
//        rabbitTemplate.setMessageConverter(messageConverter);
//        return rabbitTemplate;
//    }
//
//    @Bean
//    public MessageConverter messageConverter(ObjectMapper objectMapper) {
//        return new Jackson2JsonMessageConverter(objectMapper);
//    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("matching.exchange");
    }

    @Bean
    public Queue requestQueue() {
        return new Queue("matching.request");
    }

    @Bean
    public Binding binding1(DirectExchange directExchange, Queue requestQueue) {
        return BindingBuilder.bind(requestQueue).to(directExchange).with("matching.request");
    }


    @Bean
    public Queue successQueue() {
        return new Queue("matching.success");
    }

    @Bean
    public Binding binding2(DirectExchange directExchange, Queue successQueue) {
        return BindingBuilder.bind(successQueue).to(directExchange).with("matching.success");
    }


    @Bean
    public Queue failedQueue() {
        return new Queue("matching.failed");
    }

    @Bean
    public Binding binding3(DirectExchange directExchange, Queue failedQueue) {
        return BindingBuilder.bind(failedQueue).to(directExchange).with("matching.failed");
    }

    @Bean
    public Queue cancelQueue() {
        return new Queue("matching.cancel");
    }

    @Bean
    public Binding binding4(DirectExchange directExchange, Queue cancelQueue) {
        return BindingBuilder.bind(cancelQueue).to(directExchange).with("matching.cancel");
    }


    @Bean
    public RabbitTemplate rabbitTemplate(
            ConnectionFactory connectionFactory,
            MessageConverter messageConverter
    ) {
        var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    @Bean
    public MessageConverter messageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    }



