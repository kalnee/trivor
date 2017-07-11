package com.kalnee.trivor.insights.repositories;

import java.util.List;

public interface InsightsRepositoryCustom {

    List<Object> findInsightsByCodeAndGenre(String code, String genre);

}
