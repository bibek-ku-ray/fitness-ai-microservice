package com.fitness.aiservice.service;

import com.fitness.aiservice.model.Activity;

public interface ActivityAIService {
    void generateRecommendation(Activity activity);
    String createPromptForActivity(Activity activity);
}
