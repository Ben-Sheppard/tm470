package org.bensheppard.service;

import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class EmotionDetectionService {
  private final WebClient webClient;

  public EmotionDetectionService(
      final WebClient webClient
  ) {
    this.webClient = webClient;
  }

  public String getEmotionScores(final String content) {
    return webClient.get()
        .uri("/emotion-detect?content={content}", Map.of("content", content))
        .retrieve()
        .bodyToMono(String.class)
        .block();
  }
}
