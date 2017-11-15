package org.kalnee.trivor.insights.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.kalnee.trivor.insights.domain.insights.Insights;
import org.kalnee.trivor.insights.service.InsightService;
import org.kalnee.trivor.nlp.domain.ChunkFrequency;
import org.kalnee.trivor.nlp.domain.PhrasalVerbUsage;
import org.kalnee.trivor.nlp.domain.SentenceFrequency;
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

    @GetMapping("/sentences/frequency")
    @Timed
    public ResponseEntity<List<SentenceFrequency>> findSentencesFrequency(@RequestParam("imdbId") String imdbId,
                                                                          @RequestParam(value = "limit", required = false) Integer limit) {
        return ResponseEntity.ok().body(
            insightService.findSentencesFrequencyByImdbId(imdbId, limit)
        );
    }

    @GetMapping("/chunks/frequency")
    @Timed
    public ResponseEntity<List<ChunkFrequency>> findChunksFrequency(@RequestParam("imdbId") String imdbId,
                                                                    @RequestParam(value = "limit", required = false) Integer limit) {
        return ResponseEntity.ok().body(
            insightService.findChunksFrequencyByImdbId(imdbId, limit)
        );
    }

    @GetMapping("/phrasal-verbs/usage")
    @Timed
    public ResponseEntity<List<PhrasalVerbUsage>> findPhrasalVerbsUsageByImdbId(@RequestParam("imdbId") String imdbId) {
        return ResponseEntity.ok().body(insightService.findPhrasalVerbsUsageByImdbId(imdbId));
    }

    @GetMapping("/vocabulary/{vocabulary}/frequency")
    @Timed
    public ResponseEntity<Map<String, Integer>> findVocabularyFrequencyByImdbId(
        @PathVariable("vocabulary") String vocabulary,
        @RequestParam("imdbId") String imdbId,
        @RequestParam(value = "limit", required = false) Integer limit) {
        return ResponseEntity.ok().body(insightService.findVocabularyFrequencyByImdbId(vocabulary, imdbId, limit));
    }

    @GetMapping("/vocabulary/{vocabulary}/usage")
    @Timed
    public ResponseEntity<List<WordUsage>> findVocabularyUsageByImdbAndSeasonAndEpisode(
        @PathVariable("vocabulary") String vocabulary,
        @RequestParam("imdbId") String imdbId,
        @RequestParam(value = "season", required = false) Integer season,
        @RequestParam(value = "episode", required = false) Integer episode) {
        return ResponseEntity.ok().body(
            insightService.findVocabularyUsageByImdbAndSeasonAndEpisode(vocabulary, imdbId, season, episode)
        );
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
