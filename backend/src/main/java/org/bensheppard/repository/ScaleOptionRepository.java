package org.bensheppard.repository;

import java.util.List;
import org.bensheppard.records.ScaleOption;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Repository;

@Repository
public class ScaleOptionRepository {
  private final Jdbi jdbi;

  public ScaleOptionRepository(final Jdbi jdbi) {
    this.jdbi = jdbi;
  }

  public List<ScaleOption> getScaleOptions() {
    return jdbi.withHandle(handle -> handle.createQuery(
            "SELECT uuid, name, emoji_code " +
                "FROM mymood.scale_options " +
                "ORDER BY position"
        ).mapTo(ScaleOption.class)
        .list()
    );
  }

  public int getScaleOptionId(final String uuid) {
    return jdbi.withHandle(handle -> handle.createQuery(
        "SELECT id " +
            "FROM mymood.scale_options " +
            "WHERE uuid = :uuid "
        ).bind("uuid", uuid)
        .mapTo(Integer.class)
        .one()
    );
  }
}
