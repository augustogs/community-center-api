package com.communitycenters.api.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue communityCenterQueue() {
        return new Queue("communityCenterQueue", true);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange("communityCenterExchange");
    }

    @Bean
    public Binding binding(Queue communityCenterQueue, TopicExchange exchange) {
        return BindingBuilder.bind(communityCenterQueue).to(exchange).with("communityCenterNotification");
    }
}

