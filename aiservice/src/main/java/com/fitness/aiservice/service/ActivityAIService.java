package com.fitness.aiservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fitness.aiservice.model.Activity;
import com.fitness.aiservice.model.Recommendation;

import java.util.List;

public interface ActivityAIService {
    Recommendation generateRecommendation(Activity activity);
    String createPromptForActivity(Activity activity);
    Recommendation processAIResponse(Activity activity, String aiResponse);
    Recommendation createDefaultRecommendation(Activity activity);
    void addAnalysisSection(StringBuilder fullAnalysis, JsonNode analysisNode, String key, String prefix);
    List<String> extractSafetyGuidelines(JsonNode safetyNode);
    List<String> extractImprovement(JsonNode improvementNode);
    List<String> extractSuggestions(JsonNode suggestionsNode);
}
