package org.kalnee.trivor.insights.repository;

import org.kalnee.trivor.insights.domain.Transcript;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.util.List;

public interface TranscriptRepository extends MongoRepository<Transcript, BigInteger> {

    List<Transcript> findByExternalIdAndName(@Param("externalId") String externalId, @Param("name") String name);
}
