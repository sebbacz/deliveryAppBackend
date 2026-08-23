package be.sebastiangondek.kdg.restaurants.core;

import be.sebastiangondek.kdg.restaurants.ports.in.AddCriteriaEventCmd;
import be.sebastiangondek.kdg.restaurants.ports.in.AddCriteriaEventUseCase;
import be.sebastiangondek.kdg.restaurants.ports.out.PriceRangeCriteriaEventRepositoryPort;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

//Seeds default price range criteria events on startup if none exist.
//Criteria reflect the spec: cheap <€10, regular €11-€30, expensive €31-€60, premium >€60.

@Component
public class PriceRangeCriteriaSeeder implements ApplicationRunner {

    private final PriceRangeCriteriaEventRepositoryPort criteriaRepo;
    private final AddCriteriaEventUseCase addCriteriaEventUseCase;

    public PriceRangeCriteriaSeeder(PriceRangeCriteriaEventRepositoryPort criteriaRepo,
                                     AddCriteriaEventUseCase addCriteriaEventUseCase) {
        this.criteriaRepo = criteriaRepo;
        this.addCriteriaEventUseCase = addCriteriaEventUseCase;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (!criteriaRepo.findAllOrderedByEffectiveAt().isEmpty()) {
            return; // already seeded
        }
        // Seed initial criteria with the spec defaults, starting 12 months ago
        addCriteriaEventUseCase.addCriteriaEvent(
                new AddCriteriaEventCmd(LocalDateTime.now().minusMonths(12), 10.0, 30.0, 60.0));
    }
}
