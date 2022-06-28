package org.bensheppard.records;

import java.util.Date;

public record LogCounts(
    Date date,
    int positive,
    int neutral,
    int negative
) {
}
