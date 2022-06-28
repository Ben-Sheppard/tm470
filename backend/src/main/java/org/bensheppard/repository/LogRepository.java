package org.bensheppard.repository;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import org.bensheppard.dto.LogDTO;
import org.bensheppard.records.EmotionCounts;
import org.bensheppard.records.Log;
import org.bensheppard.records.LogCounts;
import org.bensheppard.records.LogHistoryCount;
import org.jdbi.v3.core.Jdbi;
import org.postgresql.util.PGobject;
import org.springframework.stereotype.Repository;

@Repository
public class LogRepository {
  private final Jdbi jdbi;

  public LogRepository(
      final Jdbi jdbi
  ) {
    this.jdbi = jdbi;
  }

  public void store(
      final Log log,
      final String emotionScores,
      final int scaleOptionId
  ) throws SQLException {
    PGobject emotionScoresJson = new PGobject();
    emotionScoresJson.setType("jsonb");
    emotionScoresJson.setValue(emotionScores);

    jdbi.useHandle(
        handle -> handle.createUpdate("INSERT INTO mymood.logs (" +
                "   uuid, content, scale_option_selection, emotion_scores" +
                ") VALUES (" +
                "   :uuid, :content, :scale_option_selection, :emotion_scores" +
                ")"
        )
            .bind("uuid", UUID.randomUUID())
            .bind("content", log.moodContext())
            .bind("scale_option_selection", scaleOptionId)
            .bind("emotion_scores", emotionScoresJson)
            .execute()
    );
  }

  public LogHistoryCount logHistoryCount(final int previousDays) {
    return jdbi.withHandle(
        handle -> handle.createQuery("SELECT " +
            "    COUNT(*) total, " +
            "    count(*) FILTER (WHERE mymood.scale_options.classification = 'positive') positive, " +
            "    count(*) FILTER (WHERE mymood.scale_options.classification = 'neutral') neutral, " +
            "    count(*) FILTER (WHERE mymood.scale_options.classification = 'negative') negative " +
            "FROM " +
            "    mymood.logs " +
            "JOIN " +
            "    mymood.scale_options ON mymood.scale_options.id = mymood.logs.scale_option_selection " +
            "WHERE " +
            "    mymood.logs.created > now() - INTERVAL '" + previousDays + " days'"
    )
            .mapTo(LogHistoryCount.class)
            .one()
    );
  }

  public List<LogCounts> logCounts(final int previousDays) {
    return jdbi.withHandle(
        handle -> handle.createQuery("SELECT " +
                "    DATE(DATE_TRUNC('day', mymood.logs.created)) date, " +
                "    count(*) FILTER (WHERE mymood.scale_options.classification = 'positive') positive, " +
                "    count(*) FILTER (WHERE mymood.scale_options.classification = 'neutral') neutral, " +
                "    count(*) FILTER (WHERE mymood.scale_options.classification = 'negative') negative " +
                "FROM " + "    mymood.logs " + "JOIN " +
                "    mymood.scale_options ON mymood.scale_options.id = mymood.logs.scale_option_selection " +
                "WHERE " + "    mymood.logs.created > now() - INTERVAL '" +
                previousDays + " days'" + "GROUP BY " +
                "    DATE_TRUNC('day', mymood.logs.created)"
            )
            .mapTo(LogCounts.class)
            .list()
    );
  }

  public List<EmotionCounts> emotionCounts(final int previousDays) {
    final String sql = """
            WITH emotion_scores AS (
                SELECT mymood.logs.id as id, emotion.label as emotion, emotion.score, ROW_NUMBER() OVER (PARTITION BY mymood.logs.id ORDER BY emotion.score DESC) as rank
                FROM mymood.logs, jsonb_to_recordset(mymood.logs.emotion_scores[0]) AS emotion(label text, score float)
                WHERE mymood.logs.created > now() - interval '%s days'
            )

            SELECT
                DATE(DATE_TRUNC('day', mymood.logs.created)) date,
                count(*) FILTER (WHERE emotion_scores.emotion = 'joy') joy,
                count(*) FILTER (WHERE emotion_scores.emotion = 'anger') anger,
                count(*) FILTER (WHERE emotion_scores.emotion = 'fear') fear,
                count(*) FILTER (WHERE emotion_scores.emotion = 'sadness') sadness,
                count(*) FILTER (WHERE emotion_scores.emotion = 'love') love,
                count(*) FILTER (WHERE emotion_scores.emotion = 'surprise') surprise
            FROM emotion_scores\s
            JOIN mymood.logs ON mymood.logs.id = emotion_scores.id
            WHERE rank = 1
            GROUP BY DATE_TRUNC('day', mymood.logs.created)
        """;
    return jdbi.withHandle(
        handle -> handle.createQuery(sql.formatted(previousDays))
            .mapTo(EmotionCounts.class)
            .list()
    );
  }

  public List<LogDTO> exportLogs() {
    final String sql = """
          SELECT content, created
          FROM mymood.logs
          ORDER BY created
        """;

    return jdbi.withHandle(
        handle -> handle.createQuery(sql)
            .mapTo(LogDTO.class)
            .list()
    );
  }


}
