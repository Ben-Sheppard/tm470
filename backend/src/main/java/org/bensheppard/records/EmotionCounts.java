package org.bensheppard.records;

import java.util.Date;

public record EmotionCounts(
    Date date,
    int joy,
    int anger,
    int fear,
    int sadness,
    int love,
    int surprise
) {
}
