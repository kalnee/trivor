package com.kalnee.trivor.engine.repositories;

import com.kalnee.trivor.engine.models.Insights;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.math.BigInteger;
import java.util.List;

@RepositoryRestResource(path = "insights", collectionResourceRel = "insights")
public interface InsightsRepository extends PagingAndSortingRepository<Insights, BigInteger> {
  Page<Insights> findByImdbId(@Param("imdbId") String imdbId, Pageable pageRequest);
  List<Insights> findAllByImdbId(@Param("imdbId") String imdbId);
  List<Insights> findByImdbIdAndSubtitleId(@Param("imdbId") String imdbId, @Param("subtitleId") BigInteger subtitleId);
  List<Insights> findBySubtitleId(@Param("subtitleId") String subtitleId);
}
