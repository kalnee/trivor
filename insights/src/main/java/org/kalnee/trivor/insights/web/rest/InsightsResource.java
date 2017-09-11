package org.kalnee.trivor.insights.web.rest;

import org.kalnee.trivor.insights.service.InsightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/api/insights")
public class InsightsResource {

    private final InsightService insightService;

    @Autowired
    public InsightsResource(InsightService insightService) {
        this.insightService = insightService;
    }

    @GetMapping("/{insight}")
    public ResponseEntity<Map<String, Long>> findByInsightAndImdb(@PathVariable("insight") String insight,
                                                                  @RequestParam("imdbId") String imdbId) {
        return ResponseEntity.ok().body(insightService.findByInsightAndImdb(insight, imdbId));
    }

    @GetMapping("/{insight}/genres/{genre}")
    public ResponseEntity<List<Object>> findInsightsByGenre(@PathVariable("insight") String insight,
                                                            @PathVariable("genre") String genre) {
        return ResponseEntity.ok().body(insightService.findInsightsByInsightAndGenre(insight, genre));
    }

    @GetMapping("/{insight}/keywords/{keyword}")
    public ResponseEntity<List<Object>> findInsightsByKeyword(@PathVariable("insight") String insight,
                                                              @PathVariable("keyword") String keyword) {
        return ResponseEntity.ok().body(insightService.findInsightsByInsightAndKeyword(insight, keyword));
    }
}
