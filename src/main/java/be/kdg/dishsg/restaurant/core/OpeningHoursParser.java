package be.kdg.dishsg.restaurant.core;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.EnumMap;
import java.util.Map;

/**
 * Parses the weekly opening hours string stored per restaurant.
 * Expected format (produced by the frontend):
 *   "Mon 09:00-22:00, Tue 09:00-22:00, Wed closed, Thu 09:00-22:00, Fri 09:00-22:00, Sat 10:00-23:00, Sun closed"
 * The separator between from/to times is an en-dash (U+2013).
 */
public final class OpeningHoursParser {

    private OpeningHoursParser() {}

    public record TimeRange(LocalTime from, LocalTime to) {
        public boolean contains(LocalTime time) {
            return !time.isBefore(from) && time.isBefore(to);
        }
    }

    /**
     * Parses the opening hours string into a map of DayOfWeek → TimeRange.
     * Days marked as "closed" are absent from the map.
     */
    public static Map<DayOfWeek, TimeRange> parse(String openingHours) {
        Map<DayOfWeek, TimeRange> result = new EnumMap<>(DayOfWeek.class);
        if (openingHours == null || openingHours.isBlank()) return result;

        for (String segment : openingHours.split(",\\s*")) {
            String part = segment.trim();
            int spaceIdx = part.indexOf(' ');
            if (spaceIdx < 0) continue;

            String dayAbbr = part.substring(0, spaceIdx);
            String rest = part.substring(spaceIdx + 1).trim();

            DayOfWeek day = parseDay(dayAbbr);
            if (day == null || rest.equalsIgnoreCase("closed")) continue;

            // Split on en-dash (U+2013) or regular hyphen; both may appear
            String[] times = rest.split("–|-", 2);
            if (times.length != 2) continue;

            try {
                LocalTime from = LocalTime.parse(times[0].trim());
                LocalTime to   = LocalTime.parse(times[1].trim());
                result.put(day, new TimeRange(from, to));
            } catch (Exception ignored) {
                // Malformed time — skip this day
            }
        }
        return result;
    }

    /**
     * Returns true if the current local time falls within the parsed schedule for today.
     */
    public static boolean isOpenNow(String openingHours) {
        Map<DayOfWeek, TimeRange> schedule = parse(openingHours);
        DayOfWeek today = LocalDate.now().getDayOfWeek();
        TimeRange range = schedule.get(today);
        if (range == null) return false;
        return range.contains(LocalTime.now());
    }

    private static DayOfWeek parseDay(String abbr) {
        return switch (abbr) {
            case "Mon" -> DayOfWeek.MONDAY;
            case "Tue" -> DayOfWeek.TUESDAY;
            case "Wed" -> DayOfWeek.WEDNESDAY;
            case "Thu" -> DayOfWeek.THURSDAY;
            case "Fri" -> DayOfWeek.FRIDAY;
            case "Sat" -> DayOfWeek.SATURDAY;
            case "Sun" -> DayOfWeek.SUNDAY;
            default    -> null;
        };
    }
}
