package org.kalnee.trivor.insights.repository;

import org.kalnee.trivor.insights.domain.insights.InsightsByYear;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.util.Optional;

public interface InsightsByYearRepository extends PagingAndSortingRepository<InsightsByYear, BigInteger> {

    Optional<InsightsByYear> findOneByYear(@Param("year") Integer year);
}
