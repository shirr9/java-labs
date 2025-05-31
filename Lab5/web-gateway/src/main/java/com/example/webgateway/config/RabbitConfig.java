package com.example.webgateway.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String PETS_QUEUE = "pets.queue";
    public static final String OWNERS_QUEUE = "owners.queue";
    public static final String REPLY_QUEUE = "gateway.reply.queue";

    @Bean
    public Queue replyQueue() {
        return new Queue(REPLY_QUEUE);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(CachingConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setReplyTimeout(5000);
        return template;
    }
}
