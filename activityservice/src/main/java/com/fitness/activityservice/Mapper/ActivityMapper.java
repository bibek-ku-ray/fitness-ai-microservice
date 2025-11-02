package com.fitness.activityservice.Mapper;

import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.dto.ActivityResponse;
import com.fitness.activityservice.model.Activity;

public class ActivityMapper {
    public static Activity toEntity(ActivityRequest request){
        return Activity.builder()
                .userId(request.getUserId())
                .type(request.getType())
                .startTime(request.getStartTime())
                .duration(request.getDuration())
                .caloriesBurned(request.getCaloriesBurned())
                .build();
    }

    public static ActivityResponse toDto(Activity activity){
        return ActivityResponse.builder()
                .id(activity.getId())
                .userId(activity.getUserId())
                .type(activity.getType())
                .startTime(activity.getStartTime())
                .duration(activity.getDuration())
                .caloriesBurned(activity.getCaloriesBurned())
                .additionalMetrics(activity.getAdditionalMetrics())
                .createdAt(activity.getCreatedAt())
                .updatedAt(activity.getUpdatedAt())
                .build();
    }
}
