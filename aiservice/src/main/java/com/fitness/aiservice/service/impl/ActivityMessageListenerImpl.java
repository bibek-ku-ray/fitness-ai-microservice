package com.fitness.aiservice.service.impl;

import com.fitness.aiservice.model.Activity;
import com.fitness.aiservice.service.ActivityMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ActivityMessageListenerImpl implements ActivityMessageListener {
    @Override
    @KafkaListener(topics = "${kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void processActivity(Activity activity) {
        log.info("Received Activity for processing: {}", activity.getUserId());
    }
}
