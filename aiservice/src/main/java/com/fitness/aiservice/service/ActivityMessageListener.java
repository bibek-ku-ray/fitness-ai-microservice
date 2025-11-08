package com.fitness.aiservice.service;

import com.fitness.aiservice.model.Activity;

public interface ActivityMessageListener {
    void processActivity(Activity activity);
}
