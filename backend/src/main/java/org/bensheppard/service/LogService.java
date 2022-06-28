package org.bensheppard.service;

import java.sql.SQLException;
import java.util.List;
import org.bensheppard.dto.LogDTO;
import org.bensheppard.records.EmotionCounts;
import org.bensheppard.records.Log;
import org.bensheppard.records.LogCounts;
import org.bensheppard.records.LogHistoryCount;
import org.bensheppard.repository.LogRepository;
import org.springframework.stereotype.Service;

@Service
public class LogService {
  private final EmotionDetectionService emotionDetectionService;
  private final LogRepository logRepository;
  private final ScaleOptionService scaleOptionService;

  public LogService(
      final EmotionDetectionService emotionDetectionService,
      final LogRepository logRepository,
      final ScaleOptionService scaleOptionService
  ) {
    this.emotionDetectionService = emotionDetectionService;
    this.logRepository = logRepository;
    this.scaleOptionService = scaleOptionService;
  }

  public void storeLog(final Log log) throws SQLException {
    String emotionScores = emotionDetectionService.getEmotionScores(log.moodContext());
    int scaleOptionId = scaleOptionService.getScaleOptionId(log.moodSelect());

    logRepository.store(log, emotionScores, scaleOptionId);
  }

  public LogHistoryCount logHistoryCount(final int previousDays) {
    return logRepository.logHistoryCount(previousDays);
  }

  public List<LogCounts> logCounts(final int previousDays) {
    return logRepository.logCounts(previousDays);
  }

  public List<EmotionCounts> emotionCounts(final int previousDays) {
    return logRepository.emotionCounts(previousDays);
  }

  public List<LogDTO> exportLogs() {
    return logRepository.exportLogs();
  }
}
