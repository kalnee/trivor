package org.kalnee.trivor.gateway.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.kalnee.trivor.gateway.domain.Transcript;

import org.kalnee.trivor.gateway.repository.TranscriptRepository;
import org.kalnee.trivor.gateway.web.rest.util.HeaderUtil;
import org.kalnee.trivor.gateway.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Transcript.
 */
@RestController
@RequestMapping("/api")
public class TranscriptResource {

    private final Logger log = LoggerFactory.getLogger(TranscriptResource.class);

    private static final String ENTITY_NAME = "transcript";

    private final TranscriptRepository transcriptRepository;
    public TranscriptResource(TranscriptRepository transcriptRepository) {
        this.transcriptRepository = transcriptRepository;
    }

    /**
     * POST  /transcripts : Create a new transcript.
     *
     * @param transcript the transcript to create
     * @return the ResponseEntity with status 201 (Created) and with body the new transcript, or with status 400 (Bad Request) if the transcript has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/transcripts")
    @Timed
    public ResponseEntity<Transcript> createTranscript(@Valid @RequestBody Transcript transcript) throws URISyntaxException {
        log.debug("REST request to save Transcript : {}", transcript);
        if (transcript.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new transcript cannot already have an ID")).body(null);
        }
        Transcript result = transcriptRepository.save(transcript);
        return ResponseEntity.created(new URI("/api/transcripts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /transcripts : Updates an existing transcript.
     *
     * @param transcript the transcript to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated transcript,
     * or with status 400 (Bad Request) if the transcript is not valid,
     * or with status 500 (Internal Server Error) if the transcript couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/transcripts")
    @Timed
    public ResponseEntity<Transcript> updateTranscript(@Valid @RequestBody Transcript transcript) throws URISyntaxException {
        log.debug("REST request to update Transcript : {}", transcript);
        if (transcript.getId() == null) {
            return createTranscript(transcript);
        }
        Transcript result = transcriptRepository.save(transcript);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, transcript.getId().toString()))
            .body(result);
    }

    /**
     * GET  /transcripts : get all the transcripts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of transcripts in body
     */
    @GetMapping("/transcripts")
    @Timed
    public ResponseEntity<List<Transcript>> getAllTranscripts(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Transcripts");
        Page<Transcript> page = transcriptRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/transcripts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /transcripts/imdb/:imdbId : get the "imdbId" transcript.
     *
     * @param imdbId the imdbId of the transcript to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the transcript, or with status 404 (Not Found)
     */
    @GetMapping("/transcripts/imdb/{imdbId}")
    @Timed
    public ResponseEntity<Transcript> getTranscript(@PathVariable String imdbId) {
        log.debug("REST request to get Transcript : {}", imdbId);
        Transcript transcript = transcriptRepository.findOneByImdbId(imdbId);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(transcript));
    }

    /**
     * GET  /transcripts/:id : get the "id" transcript.
     *
     * @param id the id of the transcript to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the transcript, or with status 404 (Not Found)
     */
    @GetMapping("/transcripts/{id}")
    @Timed
    public ResponseEntity<Transcript> getTranscript(@PathVariable Long id) {
        log.debug("REST request to get Transcript : {}", id);
        Transcript transcript = transcriptRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(transcript));
    }

    /**
     * DELETE  /transcripts/:id : delete the "id" transcript.
     *
     * @param id the id of the transcript to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/transcripts/{id}")
    @Timed
    public ResponseEntity<Void> deleteTranscript(@PathVariable Long id) {
        log.debug("REST request to delete Transcript : {}", id);
        transcriptRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
