package org.bensheppard.config;

import org.bensheppard.dto.LogDTO;
import org.bensheppard.records.EmotionCounts;
import org.bensheppard.records.LogCounts;
import org.bensheppard.records.LogHistoryCount;
import org.bensheppard.records.ScaleOption;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.postgres.PostgresPlugin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {

  @Bean
  public Jdbi jdbi() {
    Jdbi jdbi = Jdbi.create(
        "jdbc:postgresql://localhost:5432/my-mood-db",
        "my-mood",
        "2<8NPb[bJs5Y")
        .installPlugin(new PostgresPlugin());

    jdbi.registerRowMapper(
        ScaleOption.class,
        (rs, ctx) ->
            new ScaleOption(
                rs.getString("uuid"),
                rs.getString("name"),
                rs.getString("emoji_code")
            )
    );

    jdbi.registerRowMapper(
        LogHistoryCount.class,
        (rs, ctx) ->
            new LogHistoryCount(
                rs.getInt("total"),
                rs.getInt("positive"),
                rs.getInt("neutral"),
                rs.getInt("negative")
            )
    );

    jdbi.registerRowMapper(
        LogCounts.class,
        (rs, ctx) ->
            new LogCounts(
                rs.getDate("date"),
                rs.getInt("positive"),
                rs.getInt("neutral"),
                rs.getInt("negative")
            )
    );

    jdbi.registerRowMapper(
        EmotionCounts.class,
        (rs, ctx) ->
            new EmotionCounts(
                rs.getDate("date"),
                rs.getInt("joy"),
                rs.getInt("anger"),
                rs.getInt("fear"),
                rs.getInt("sadness"),
                rs.getInt("love"),
                rs.getInt("surprise")
            )
    );

    jdbi.registerRowMapper(
        LogDTO.class,
        (rs, ctx) ->
            new LogDTO(
                rs.getString("content"),
                rs.getDate("created")
            )
    );

    return jdbi;
  }

  @Bean
  public WebClient webClient() {
    return WebClient.create("http://localhost:8085");
  }
}
