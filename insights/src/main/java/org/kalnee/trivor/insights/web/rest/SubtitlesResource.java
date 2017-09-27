package org.kalnee.trivor.insights.web.rest;

import org.kalnee.trivor.insights.domain.dto.TVShowDTO;
import org.kalnee.trivor.insights.service.SubtitleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/subtitles")
public class SubtitlesResource {

    private final SubtitleService subtitleService;

    public SubtitlesResource(SubtitleService subtitleService) {
        this.subtitleService = subtitleService;
    }

    @GetMapping("/tv-show/meta")
    public ResponseEntity<TVShowDTO> findTvShowByImdb(@RequestParam("imdbId") String imdbId) {
        return ResponseEntity.ok().body(subtitleService.findTvShowByImdbCached(imdbId));
    }
}
