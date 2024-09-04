package com.communitycenters.api.service;

import com.communitycenters.api.model.CommunityCenter;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendCapacityReachedNotification(CommunityCenter center) {
        String message = String.format("Attention: The community center '%s' has reached its maximum capacity of %d people.",
                center.getName(), center.getCapacity());
        rabbitTemplate.convertAndSend("communityCenterExchange", "communityCenterNotification", message);
    }

}

