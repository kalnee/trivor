package org.kalnee.trivor.insights.repository;

import org.kalnee.trivor.insights.domain.Insights;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface InsightsRepository extends PagingAndSortingRepository<Insights, BigInteger> {

  Page<Insights> findByImdbId(@Param("imdbId") String imdbId, Pageable pageRequest);

  List<Insights> findAllByImdbId(@Param("imdbId") String imdbId);

  Insights findOneByImdbId(@Param("imdbId") String imdbId);

  List<Insights> findAllByImdbIdAndSubtitleId(@Param("imdbId") String imdbId, @Param("subtitleId") String subtitleId);

  Insights findOneByImdbIdAndSubtitleId(@Param("imdbId") String imdbId, @Param("subtitleId") String subtitleId);

  List<Insights> findByImdbIdIn(List<String> imdbIdList);
}
