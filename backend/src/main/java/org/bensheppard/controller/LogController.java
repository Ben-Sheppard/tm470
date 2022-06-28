package org.bensheppard.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.bensheppard.dto.LogDTO;
import org.bensheppard.records.EmotionCounts;
import org.bensheppard.records.Log;
import org.bensheppard.records.LogCounts;
import org.bensheppard.records.LogHistoryCount;
import org.bensheppard.service.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/logs")
public class LogController {
  private static final Logger LOGGER = LoggerFactory.getLogger(LogController.class);
  private static final ByteArrayOutputStream BYTE_ARRAY_OUTPUT_STREAM = new ByteArrayOutputStream();

  private final LogService logService;

  public LogController(
      final LogService logService
  ) {
    this.logService = logService;
  }

  @PostMapping
  public void createLog(
      @RequestBody final Log log
  ) throws SQLException {
    LOGGER.info("Received log {}", log);
    logService.storeLog(log);
  }

  @GetMapping("/history/counts/headline")
  public LogHistoryCount LogHistoryCount(
      @RequestParam(value = "previousDays", required = false, defaultValue = "7") final Integer previousDays
  ) {
    return logService.logHistoryCount(previousDays);
  }

  @GetMapping("/history/by-day/counts")
  public List<LogCounts> logCounts(
      @RequestParam(value = "previousDays", required = false, defaultValue = "7") final Integer previousDays
  ) {
    return logService.logCounts(previousDays);
  }

  @GetMapping("/history/by-day/counts/emotions")
  public List<EmotionCounts> emotionCounts(
      @RequestParam(value = "previousDays", required = false, defaultValue = "7") final Integer previousDays
  ) {
    return logService.emotionCounts(previousDays);
  }

  @GetMapping("/history/export")
  public void exportLogs(
      final HttpServletResponse response
  ) {
    ContentDisposition contentDisposition = ContentDisposition.attachment()
        .filename("test.csv")
        .build();
    response.setHeader(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());
    response.setContentType("text/csv");

    List<LogDTO> logs = logService.exportLogs();
    try (CSVPrinter csvPrinter = new CSVPrinter(response.getWriter(), CSVFormat.DEFAULT)) {
      logs.forEach(logDTO -> {
        try {
          csvPrinter.printRecord(logDTO.getContent(), logDTO.getCreated());
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      });
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
