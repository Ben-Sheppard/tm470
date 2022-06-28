package org.bensheppard.controller;

import java.util.List;
import org.bensheppard.records.ScaleOption;
import org.bensheppard.service.ScaleOptionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/scale-options")
public class ScaleOptionController {
  private final ScaleOptionService scaleOptionService;

  public ScaleOptionController(final ScaleOptionService scaleOptionService) {
    this.scaleOptionService = scaleOptionService;
  }

  @GetMapping
  public List<ScaleOption> getScaleOptions() {
    return scaleOptionService.getScaleOptions();
  }
}
