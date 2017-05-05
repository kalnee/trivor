package com.kalnee.trivor.engine.controllers;

import static java.lang.String.format;
import static java.util.stream.Collectors.toMap;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kalnee.trivor.engine.models.Insights;
import com.kalnee.trivor.engine.repositories.InsightsRepository;

@RestController
@RequestMapping(value = "/insights")
public class InsightsController {

  private final InsightsRepository insightsRepository;

  @Autowired
  public InsightsController(InsightsRepository insightsRepository) {
    this.insightsRepository = insightsRepository;
  }

  @RequestMapping(value = "/{imdbId}/codes/{code}", method = GET)
  public Response findByImdbIdAndCode(@PathVariable("imdbId") String imdbId,
      @PathVariable("code") String code) {
    final List<Insights> insights = null;

    final Map<String, Long> map = new HashMap<>();

    insightsRepository.findByImdbId(imdbId).stream().flatMap(i -> i.getInsights().stream())
        .filter(i -> i.getCode().equals(code))
        .flatMap(i -> ((LinkedHashMap<String, Long>) i.getValue()).entrySet().stream())
        .forEach(i -> map.put(i.getKey(), i.getValue() + map.getOrDefault(i.getKey(), 0L)));

    final Map<String, Long> map2 =
        map.entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            // .limit(10)
            .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> {
              throw new RuntimeException(format("Duplicate key for values %s and %s", v1, v2));
            }, LinkedHashMap::new));

    return Response.ok().entity(map2).build();
  }
}
