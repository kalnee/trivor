package com.kalnee.trivor.insights.controllers;

import com.kalnee.trivor.insights.repositories.InsightsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.lang.String.format;
import static java.util.stream.Collectors.toMap;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(value = "/insights")
public class InsightsController {

    private final InsightsRepository insightsRepository;

    @Autowired
    public InsightsController(InsightsRepository insightsRepository) {
        this.insightsRepository = insightsRepository;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/{imdbId}/codes/{code}", method = GET)
    public Response findByImdbIdAndCode(@PathVariable("imdbId") String imdbId, @PathVariable("code") String code) {

        final Map<String, Long> allInsights = new HashMap<>();

        insightsRepository.findAllByImdbId(imdbId).stream().flatMap(i -> i.getInsights().entrySet().stream())
                .filter(i -> i.getKey().equals(code))
                .flatMap(i -> ((LinkedHashMap<String, Long>) i.getValue()).entrySet().stream())
                .forEach(i -> allInsights.put(i.getKey(), i.getValue() + allInsights.getOrDefault(i.getKey(), 0L)));

        final Map<String, Long> sortedInsights =
                allInsights.entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                        // .limit(10)
                        .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> {
                            throw new RuntimeException(format("Duplicate key for values %s and %s", v1, v2));
                        }, LinkedHashMap::new));

        return Response.ok().entity(sortedInsights).build();
    }
}
