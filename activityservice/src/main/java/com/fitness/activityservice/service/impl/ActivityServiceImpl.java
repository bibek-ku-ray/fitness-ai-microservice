package com.fitness.activityservice.service.impl;

import com.fitness.activityservice.Mapper.ActivityMapper;
import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.dto.ActivityResponse;
import com.fitness.activityservice.model.Activity;
import com.fitness.activityservice.repository.ActivityRepository;
import com.fitness.activityservice.service.ActivityService;
import com.fitness.activityservice.service.UserValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;
    private final UserValidationService userValidationService;
    private final KafkaTemplate<String, Activity> kafkaTemplate;

    @Value("${kafka.topic.name}")
    private String topicName;

    @Override
    public ActivityResponse trackActivity(ActivityRequest request) {

        boolean isValidUser = userValidationService.validateUser(request.getUserId());

        if (!isValidUser)
            throw new IllegalArgumentException("Invalid userid: "+ request.getUserId());

        Activity activity = Activity.builder()
                .userId(request.getUserId())
                .type(request.getType())
                .duration(request.getDuration())
                .caloriesBurned(request.getCaloriesBurned())
                .startTime(request.getStartTime())
                .additionalMetrics(request.getAdditionalMetrics())
                .build();

        Activity savedActivity = activityRepository.save(activity);

        try {
            kafkaTemplate.send(topicName, savedActivity.getUserId(), savedActivity)
                    .whenComplete((result, ex) -> {
                        if(ex != null) {
                            log.error("Failed to send activity to Kafka: {}", savedActivity.getId(), ex);
                        } else {
                            log.info("Activity sent successfully to Kafka: userId={}, partition={}, offset={}",
                                    savedActivity.getUserId(),
                                    result.getRecordMetadata().partition(),
                                    result.getRecordMetadata().offset());
                        }
                    });
            
            log.info("Activity saved and queued for Kafka: userId={}, activityId={}", 
                    savedActivity.getUserId(), savedActivity.getId());
        } catch (Exception e) {
            log.error("Failed to push activity to Kafka for userId: {}",
                    savedActivity.getUserId(), e);
            throw new RuntimeException("Failed to publish activity to Kafka", e);
        }

        return ActivityMapper.toDto(savedActivity);
    }


}
