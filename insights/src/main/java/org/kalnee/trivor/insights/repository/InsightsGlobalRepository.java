package org.kalnee.trivor.insights.repository;

import org.kalnee.trivor.insights.domain.insights.InsightsGlobal;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.math.BigInteger;

public interface InsightsGlobalRepository extends PagingAndSortingRepository<InsightsGlobal, BigInteger> {

}
