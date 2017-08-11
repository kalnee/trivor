package com.kalnee.trivor.insights.repositories;

import com.kalnee.trivor.insights.models.Subtitle;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.math.BigInteger;
import java.util.List;

@RepositoryRestResource(path = "subtitles")
public interface SubtitleRepository extends MongoRepository<Subtitle, BigInteger> {

  List<Subtitle> findByYear(@Param("year") Integer year);

  List<Subtitle> findByImdbId(@Param("imdbId") String imdbId);

  List<Subtitle> findByGenres(@Param("genre") String genre);

  List<Subtitle> findByKeywords(@Param("keyword") String keyword);
}
