package be.kdg.dishsg.restaurant.adapters.in.scheduler;

import be.kdg.dishsg.restaurant.core.OpeningHoursParser;
import be.kdg.dishsg.restaurant.domain.model.Restaurant;
import be.kdg.dishsg.restaurant.ports.out.RestaurantRepositoryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Automatically opens and closes restaurants based on their weekly opening hours schedule.
 * Runs every minute. Manual open/close by the owner will be overridden at the next
 * scheduled boundary (i.e., the schedule is always the ultimate authority).
 */
@Service
public class OpeningHoursSchedulerAdapter {

    private static final Logger log = LoggerFactory.getLogger(OpeningHoursSchedulerAdapter.class);

    private final RestaurantRepositoryPort restaurantRepository;

    public OpeningHoursSchedulerAdapter(RestaurantRepositoryPort restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Scheduled(fixedDelay = 60_000)
    public void syncOpenStatus() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        for (Restaurant restaurant : restaurants) {
            boolean shouldBeOpen = OpeningHoursParser.isOpenNow(restaurant.getOpeningHours());

            if (restaurant.isManualOverride()) {
                // Owner manually opened/closed — respect that until the schedule boundary passes.
                // When the schedule now agrees with the current state, the override period is over.
                if (shouldBeOpen == restaurant.isOpen()) {
                    restaurant.clearManualOverride();
                    restaurantRepository.save(restaurant);
                    log.info("Cleared manual override for '{}' — schedule and state now agree.", restaurant.getName());
                }
                // Otherwise leave it alone; the owner's intent still stands.
            } else {
                // No manual override — auto-sync to the schedule.
                if (restaurant.isOpen() != shouldBeOpen) {
                    if (shouldBeOpen) {
                        restaurant.open();
                        restaurant.clearManualOverride(); // open() sets manualOverride=true, undo that for auto
                        log.info("Auto-opened restaurant '{}' based on schedule.", restaurant.getName());
                    } else {
                        restaurant.close();
                        restaurant.clearManualOverride(); // close() sets manualOverride=true, undo that for auto
                        log.info("Auto-closed restaurant '{}' based on schedule.", restaurant.getName());
                    }
                    restaurantRepository.save(restaurant);
                }
            }
        }
    }
}
