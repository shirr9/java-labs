package com.example.petservice.listener;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfigPet {

    public static final String PET_QUEUE = "pets.queue";

    @Bean
    public Queue petQueue() {
        return new Queue(PET_QUEUE);
    }
}