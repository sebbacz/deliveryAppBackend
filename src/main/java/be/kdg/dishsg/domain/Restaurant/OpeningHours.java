package be.kdg.dishsg.domain.Restaurant;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

public class OpeningHours {

    private final List<OpeningHoursEntry> entries;

    public OpeningHours(List<OpeningHoursEntry> entries) {
        this.entries = Objects.requireNonNull(entries);
    }

    public List<OpeningHoursEntry> getEntries() {
        return entries;
    }

    public boolean isOpenAt(java.util.Date now) {
        java.time.LocalDateTime localDateTime =
                java.time.LocalDateTime.ofInstant(now.toInstant(), java.time.ZoneId.systemDefault());
        DayOfWeek day = localDateTime.getDayOfWeek();
        LocalTime time = localDateTime.toLocalTime();
        return entries.stream().anyMatch(e -> e.matches(day, time));
    }
}
