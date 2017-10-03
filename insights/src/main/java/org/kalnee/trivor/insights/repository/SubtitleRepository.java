package org.kalnee.trivor.insights.repository;

import org.kalnee.trivor.insights.domain.Subtitle;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface SubtitleRepository extends MongoRepository<Subtitle, BigInteger> {

  List<Subtitle> findByYear(@Param("year") Integer year);

  List<Subtitle> findByImdbId(@Param("imdbId") String imdbId);

  List<Subtitle> findByImdbIdAndSeasonAndEpisode(@Param("imdbId") String imdbId, @Param("season") Integer season,
                                                 @Param("episode") Integer episode);

  Optional<Subtitle> findOneByImdbIdAndSeasonAndEpisode(@Param("imdbId") String imdbId, @Param("season") Integer season,
                                                        @Param("episode") Integer episode);

  List<Subtitle> findByGenres(@Param("genre") String genre);

  List<Subtitle> findByKeywords(@Param("keyword") String keyword);
}
