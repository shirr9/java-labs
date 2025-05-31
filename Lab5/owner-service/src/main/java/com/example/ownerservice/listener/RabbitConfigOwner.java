package com.example.ownerservice.listener;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfigOwner {

    public static final String QUEUE_NAME = "owners.queue";

    @Bean
    public Queue ownerQueue() {
        return new Queue(QUEUE_NAME);
    }
}
