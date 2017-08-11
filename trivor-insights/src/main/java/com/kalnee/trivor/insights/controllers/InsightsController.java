package com.kalnee.trivor.insights.controllers;

import com.kalnee.trivor.insights.services.InsightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.Response;

@RestController
@RequestMapping(value = "/insights")
public class InsightsController {

    private final InsightService insightService;

    @Autowired
    public InsightsController(InsightService insightService) {
        this.insightService = insightService;
    }

    @RequestMapping(value = "/{insight}")
    public Response findByInsightAndImdb(@PathVariable("insight") String insight,
                                         @RequestParam("imdbId") String imdbId) {
        return Response.ok().entity(insightService.findByInsightAndImdb(insight, imdbId)).build();
    }

    @RequestMapping(value = "/{insight}/genres/{genre}")
    public Response findInsightsByGenre(@PathVariable("insight") String insight,
                                        @PathVariable("genre") String genre) {
        return Response.ok().entity(insightService.findInsightsByInsightAndGenre(insight, genre)).build();
    }

    @RequestMapping(value = "/{insight}/keywords/{keyword}")
    public Response findInsightsByKeyword(@PathVariable("insight") String insight,
                                          @PathVariable("keyword") String keyword) {
        return Response.ok().entity(insightService.findInsightsByInsightAndKeyword(insight, keyword)).build();
    }
}
