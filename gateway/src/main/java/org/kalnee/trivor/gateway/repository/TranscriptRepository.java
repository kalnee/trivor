package org.kalnee.trivor.gateway.repository;

import org.kalnee.trivor.gateway.domain.Transcript;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Transcript entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TranscriptRepository extends JpaRepository<Transcript, Long> {

    Transcript findOneByImdbId(String imdbId);
}
