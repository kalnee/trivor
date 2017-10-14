package org.kalnee.trivor.insights.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.kalnee.trivor.insights.domain.Insights;
import org.kalnee.trivor.insights.service.InsightService;
import org.kalnee.trivor.nlp.domain.WordUsage;
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

    @GetMapping("/verbs/frequency")
    @Timed
    public ResponseEntity<Map<String, Integer>> findVerbFrequencyByImdbId(
        @RequestParam("imdbId") String imdbId,
        @RequestParam(value = "limit", required = false) Integer limit) {
        return ResponseEntity.ok().body(insightService.findVerbFrequencyByImdbId(imdbId, limit));
    }

    @GetMapping("/verbs/usage")
    @Timed
    public ResponseEntity<List<WordUsage>> findVerbUsageByImdbAndSeasonAndEpisode(
        @RequestParam("imdbId") String imdbId,
        @RequestParam(value = "season", required = false) Integer season,
        @RequestParam(value = "episode", required = false) Integer episode) {
        return ResponseEntity.ok().body(
            insightService.findVerbUsageByImdbAndSeasonAndEpisode(imdbId, season, episode)
        );
    }

    @GetMapping("/verb-tenses/usage")
    @Timed
    public ResponseEntity<Set<String>> findVerbTensesByInsightAndImdb(
        @RequestParam("imdbId") String imdbId) {
        return ResponseEntity.ok().body(insightService.findVerbTensesByInsightAndImdb(imdbId));
    }

    @GetMapping("/{insight}/genres/{genre}")
    @Timed
    public ResponseEntity<List<Insights>> findInsightsByGenre(
        @PathVariable("genre") String genre) {
        return ResponseEntity.ok().body(insightService.findInsightsByInsightAndGenre(genre));
    }

    @GetMapping("/{insight}/keywords/{keyword}")
    @Timed
    public ResponseEntity<List<Insights>> findInsightsByKeyword(
        @PathVariable("keyword") String keyword) {
        return ResponseEntity.ok().body(insightService.findInsightsByInsightAndKeyword(keyword));
    }
}
