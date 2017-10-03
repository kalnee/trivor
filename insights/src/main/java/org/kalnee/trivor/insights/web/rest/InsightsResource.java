package org.kalnee.trivor.insights.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.kalnee.trivor.insights.domain.Insights;
import org.kalnee.trivor.insights.service.InsightService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;


@RestController
@RequestMapping(value = "/api/insights")
public class InsightsResource {

    private final InsightService insightService;

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
        return ResponseEntity.ok().body(insightService.getInsightsSummary(imdbId));
    }

    @GetMapping("/frequency/{insight}")
    @Timed
    public ResponseEntity<Map<String, Integer>> findFrequencyByInsightAndImdbId(@PathVariable("insight") String insight,
                                                                                @RequestParam("imdbId") String imdbId) {
        return ResponseEntity.ok().body(insightService.findFrequencyByInsightAndImdbId(insight, imdbId));
    }

    @GetMapping("/frequency/{insight}/top/{limit}")
    @Timed
    public ResponseEntity<Map<String, Integer>> findTopFrequencyByImdbId(@PathVariable("insight") String insight,
                                                                         @PathVariable("limit") Integer limit,
                                                                         @RequestParam("imdbId") String imdbId) {
        return ResponseEntity.ok().body(insightService.findTopFrequencyByInsightAndImdbId(insight, imdbId, limit));
    }

    @GetMapping("/sentences/{insight}")
    @Timed
    public ResponseEntity<Map<String, List<String>>> findSentencesByInsightAndImdb(
        @PathVariable("insight") String insight,
        @RequestParam("imdbId") String imdbId,
        @RequestParam(value = "season", required = false) Integer season,
        @RequestParam(value = "episode", required = false) Integer episode) {
        return ResponseEntity.ok().body(
            insightService.findSentencesByInsightAndImdbAndSeasonAndEpisode(insight, imdbId, season, episode)
        );
    }

    @GetMapping("/verb-tenses/{insight}")
    @Timed
    public ResponseEntity<Set<String>> findVerbTensesByInsightAndImdb(
        @PathVariable("insight") String insight,
        @RequestParam("imdbId") String imdbId) {
        return ResponseEntity.ok().body(insightService.findVerbTensesByInsightAndImdb(insight, imdbId));
    }

    @GetMapping("/{insight}/genres/{genre}")
    @Timed
    public ResponseEntity<List<Object>> findInsightsByGenre(@PathVariable("insight") String insight,
                                                            @PathVariable("genre") String genre) {
        return ResponseEntity.ok().body(insightService.findInsightsByInsightAndGenre(insight, genre));
    }

    @GetMapping("/{insight}/keywords/{keyword}")
    @Timed
    public ResponseEntity<List<Object>> findInsightsByKeyword(@PathVariable("insight") String insight,
                                                              @PathVariable("keyword") String keyword) {
        return ResponseEntity.ok().body(insightService.findInsightsByInsightAndKeyword(insight, keyword));
    }
}
