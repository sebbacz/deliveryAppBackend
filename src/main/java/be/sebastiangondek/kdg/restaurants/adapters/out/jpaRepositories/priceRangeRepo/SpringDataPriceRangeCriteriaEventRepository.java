package be.sebastiangondek.kdg.restaurants.adapters.out.jpaRepositories.priceRangeRepo;

import be.sebastiangondek.kdg.restaurants.adapters.out.jpaRepositories.priceRangeRepo.entities.PriceRangeCriteriaEventJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

// JPA repository for price range criteria events, ordered by effective date for history replay.
public interface SpringDataPriceRangeCriteriaEventRepository
        extends JpaRepository<PriceRangeCriteriaEventJpaEntity, UUID> {

    List<PriceRangeCriteriaEventJpaEntity> findAllByOrderByEffectiveAtAsc();
}
