package org.bensheppard.service;

import java.util.List;
import org.bensheppard.records.ScaleOption;
import org.bensheppard.repository.ScaleOptionRepository;
import org.springframework.stereotype.Service;

@Service
public class ScaleOptionService {
  private final ScaleOptionRepository scaleOptionRepository;

  public ScaleOptionService(final ScaleOptionRepository scaleOptionRepository) {
    this.scaleOptionRepository = scaleOptionRepository;
  }

  public List<ScaleOption> getScaleOptions() {
    return scaleOptionRepository.getScaleOptions();
  }

  public int getScaleOptionId(final String uuid) {
    return scaleOptionRepository.getScaleOptionId(uuid);
  }
}
