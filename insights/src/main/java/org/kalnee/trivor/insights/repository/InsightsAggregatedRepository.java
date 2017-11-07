package org.kalnee.trivor.insights.repository;

import org.kalnee.trivor.insights.domain.insights.InsightsAggregated;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface InsightsAggregatedRepository extends PagingAndSortingRepository<InsightsAggregated, BigInteger> {

    Optional<InsightsAggregated> findOneByImdbId(@Param("imdbId") String imdbId);

    List<InsightsAggregated> findAllByImdbId(@Param("imdbId") String imdbId);

    List<InsightsAggregated> findByImdbIdIn(Collection<String> imdbIdList);

    @Override
    List<InsightsAggregated> findAll();
}
