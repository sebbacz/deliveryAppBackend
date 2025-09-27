package be.kdg.dishsg.domain.Restaurant;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class OpeningHoursEntry{

        private final DayOfWeek day;
        private final LocalTime open;
        private final LocalTime close;

    public OpeningHoursEntry(DayOfWeek day, LocalTime open, LocalTime close) {
        this.day = day;
        this.open = open;
        this.close = close;
    }
}
