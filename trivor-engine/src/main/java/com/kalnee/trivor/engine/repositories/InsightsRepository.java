package com.kalnee.trivor.engine.repositories;

import java.math.BigInteger;
import java.util.List;

import com.kalnee.trivor.engine.models.Insights;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.kalnee.trivor.engine.models.Subtitle;

@RepositoryRestResource(path = "insights")
public interface InsightsRepository extends MongoRepository<Insights, BigInteger> {
  List<Insights> findByImdbId(@Param("imdbId") String imdbId);
}
