package com.kalnee.trivor.engine.repositories;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kalnee.trivor.engine.models.SubtitleModel;

public interface SubtitleRepository extends MongoRepository<SubtitleModel, BigInteger> {

  List<SubtitleModel> findByYear(Integer year);
}
