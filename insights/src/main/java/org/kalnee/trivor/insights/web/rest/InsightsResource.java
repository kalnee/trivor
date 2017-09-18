package org.kalnee.trivor.insights.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.kalnee.trivor.insights.domain.Insights;
import org.kalnee.trivor.insights.service.InsightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @GetMapping
    public ResponseEntity<Page<Insights>> findByInsightAndImdb(@RequestParam("imdbId") String imdbId,
                                                               Pageable pageable) {
        return ResponseEntity.ok().body(insightService.findByImdbId(imdbId, pageable));
    }

    @GetMapping("/summary")
    @Timed
    public ResponseEntity<Map<String, Object>> getInsightsSummary(@RequestParam("imdbId") String imdbId) {
        return ResponseEntity.ok().body(insightService.getInsightsSummaryCached(imdbId));
    }

    @GetMapping("/frequency/{insight}")
    public ResponseEntity<Map<String, Integer>> findFrequencyByInsightAndImdb(@PathVariable("insight") String insight,
                                                                              @RequestParam("imdbId") String imdbId) {
        return ResponseEntity.ok().body(insightService.findFrequencyByInsightAndImdb(insight, imdbId));
    }

    @GetMapping("/sentences/{insight}")
    public ResponseEntity<Map<String, List<String>>> findSentencesByInsightAndImdb(
        @PathVariable("insight") String insight,
        @RequestParam("imdbId") String imdbId) {
        return ResponseEntity.ok().body(insightService.findSentencesByInsightAndImdb(insight, imdbId));
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
