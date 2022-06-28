package org.bensheppard.dto;

import java.io.Serializable;
import java.util.Date;

public class LogDTO implements Serializable {
  private final String content;
  private final Date created;

  public LogDTO(final String content, final Date created) {
    this.content = content;
    this.created = created;
  }

  public String getContent() {
    return content;
  }

  public Date getCreated() {
    return created;
  }

  public String[] csvStringArray() {
    return new String[]{this.content, this.getCreated().toString()};
  }
}
