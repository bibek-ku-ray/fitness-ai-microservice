package com.fitness.aiservice.service.impl;

import com.fitness.aiservice.model.Activity;
import com.fitness.aiservice.model.Recommendation;
import com.fitness.aiservice.repository.RecommendationRepository;
import com.fitness.aiservice.service.ActivityAIService;
import com.fitness.aiservice.service.ActivityMessageListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityMessageListenerImpl implements ActivityMessageListener {

    private final ActivityAIService activityAIService;
    private final RecommendationRepository recommendationRepository;

    @Override
    @KafkaListener(topics = "${kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void processActivity(Activity activity) {
        if (activity == null) {
            log.warn("Received null Activity, skipping processing");
            return;
        }

        try {
            log.info("Received Activity for processing: {}", activity.getUserId());
            Recommendation recommendation = activityAIService.generateRecommendation(activity);
            if (recommendation == null) {
                log.warn("No recommendation generated for user {}", activity.getUserId());
                return;
            }
            recommendationRepository.save(recommendation);
        } catch (Exception e) {
            log.error("Failed to process activity for user {}: {}", activity.getUserId(), e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
