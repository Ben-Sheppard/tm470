package org.bensheppard.records;

public record LogHistoryCount(
    int total,
    int positive,
    int neutral,
    int negative
) {
}
