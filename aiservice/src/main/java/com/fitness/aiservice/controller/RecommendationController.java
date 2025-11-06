package com.fitness.aiservice.controller;

import com.fitness.aiservice.model.Recommendation;
import com.fitness.aiservice.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<Recommendation>> getUserRecommendations(@PathVariable String userId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(recommendationService.getUserRecommendations(userId));
    }

    @GetMapping("/activities/{activityId}")
    public ResponseEntity<Recommendation> getActivityRecommendation(@PathVariable String activityId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(recommendationService.getActivityRecommendation(activityId));
    }

}
