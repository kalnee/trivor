package com.kalnee.trivor.engine.repositories;

import java.math.BigInteger;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kalnee.trivor.engine.models.SubtitleModel;

public interface SubtitleRepository extends MongoRepository<SubtitleModel, BigInteger> {

  Iterable<SubtitleModel> findByYear(Integer year);
}
