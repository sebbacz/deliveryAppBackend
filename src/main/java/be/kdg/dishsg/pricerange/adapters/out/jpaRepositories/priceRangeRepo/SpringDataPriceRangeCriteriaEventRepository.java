package be.kdg.dishsg.pricerange.adapters.out.jpaRepositories.priceRangeRepo;

import be.kdg.dishsg.pricerange.adapters.out.jpaRepositories.priceRangeRepo.entities.PriceRangeCriteriaEventJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

// Spring Data JPA repository for price range criteria events, ordered by effective date for history replay.
public interface SpringDataPriceRangeCriteriaEventRepository
        extends JpaRepository<PriceRangeCriteriaEventJpaEntity, UUID> {

    List<PriceRangeCriteriaEventJpaEntity> findAllByOrderByEffectiveAtAsc();
}
