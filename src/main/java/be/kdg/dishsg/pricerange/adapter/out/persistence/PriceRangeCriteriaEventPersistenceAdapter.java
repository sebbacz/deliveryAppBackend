package be.kdg.dishsg.pricerange.adapter.out.persistence;

import be.kdg.dishsg.pricerange.domain.PriceRangeCriteriaEvent;
import be.kdg.dishsg.pricerange.ports.out.PriceRangeCriteriaEventRepositoryPort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PriceRangeCriteriaEventPersistenceAdapter implements PriceRangeCriteriaEventRepositoryPort {

    private final SpringDataPriceRangeCriteriaEventRepository springRepo;

    public PriceRangeCriteriaEventPersistenceAdapter(SpringDataPriceRangeCriteriaEventRepository springRepo) {
        this.springRepo = springRepo;
    }

    @Override
    public void save(PriceRangeCriteriaEvent event) {
        springRepo.save(new PriceRangeCriteriaEventJpaEntity(
                event.getId(), event.getEffectiveAt(),
                event.getCheapMax(), event.getRegularMax(), event.getExpensiveMax()
        ));
    }

    @Override
    public List<PriceRangeCriteriaEvent> findAllOrderedByEffectiveAt() {
        return springRepo.findAllByOrderByEffectiveAtAsc().stream()
                .map(e -> new PriceRangeCriteriaEvent(
                        e.getId(), e.getEffectiveAt(),
                        e.getCheapMax(), e.getRegularMax(), e.getExpensiveMax()))
                .collect(Collectors.toList());
    }
}
