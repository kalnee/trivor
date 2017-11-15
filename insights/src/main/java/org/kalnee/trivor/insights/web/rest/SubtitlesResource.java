package org.kalnee.trivor.insights.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.kalnee.trivor.insights.domain.dto.SubtitleDTO;
import org.kalnee.trivor.insights.domain.dto.TVShowDTO;
import org.kalnee.trivor.insights.service.SubtitleService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.kalnee.trivor.insights.security.AuthoritiesConstants.ADMIN;
import static org.springframework.http.HttpStatus.CREATED;


@RestController
@RequestMapping(value = "/api/subtitles")
public class SubtitlesResource {

    private final SubtitleService subtitleService;

    public SubtitlesResource(SubtitleService subtitleService) {
        this.subtitleService = subtitleService;
    }

    @PostMapping
    @Timed
    @Secured(ADMIN)
    public ResponseEntity process(@RequestBody @Valid SubtitleDTO subtitleDTO) {
        subtitleService.process(subtitleDTO);
        return ResponseEntity.status(CREATED).build();
    }

    @GetMapping("/tv-show/meta")
    public ResponseEntity<TVShowDTO> findTvShowByImdb(@RequestParam("imdbId") String imdbId) {
        return ResponseEntity.ok().body(subtitleService.findTvShowByImdbCached(imdbId));
    }
}
