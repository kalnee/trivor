package org.kalnee.trivor.insights.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.kalnee.trivor.insights.domain.dto.TranscriptDTO;
import org.kalnee.trivor.insights.service.TranscriptService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.kalnee.trivor.insights.security.AuthoritiesConstants.ADMIN;
import static org.springframework.http.HttpStatus.CREATED;


@RestController
@RequestMapping(value = "/api/transcripts")
public class TranscriptsResource {

    private final TranscriptService transcriptService;

    public TranscriptsResource(TranscriptService transcriptService) {
        this.transcriptService = transcriptService;
    }

    @PostMapping
    @Timed
    @Secured(ADMIN)
    public ResponseEntity process(@RequestBody @Valid TranscriptDTO transcriptDTO) {
        transcriptService.process(transcriptDTO);
        return ResponseEntity.status(CREATED).build();
    }
}
