package org.kalnee.trivor.gateway.web.rest;

import org.kalnee.trivor.gateway.GatewayApp;

import org.kalnee.trivor.gateway.domain.Transcript;
import org.kalnee.trivor.gateway.repository.TranscriptRepository;
import org.kalnee.trivor.gateway.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static org.kalnee.trivor.gateway.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.kalnee.trivor.gateway.domain.enumeration.TranscriptTypeEnum;
/**
 * Test class for the TranscriptResource REST controller.
 *
 * @see TranscriptResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GatewayApp.class)
public class TranscriptResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final TranscriptTypeEnum DEFAULT_TYPE = TranscriptTypeEnum.TRANSCRIPT;
    private static final TranscriptTypeEnum UPDATED_TYPE = TranscriptTypeEnum.MOVIE;

    private static final String DEFAULT_IMDB_ID = "AAAAAAAAAA";
    private static final String UPDATED_IMDB_ID = "BBBBBBBBBB";

    private static final String DEFAULT_COVER_URL = "AAAAAAAAAA";
    private static final String UPDATED_COVER_URL = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;

    private static final String DEFAULT_GENRES = "AAAAAAAAAA";
    private static final String UPDATED_GENRES = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAA";
    private static final String UPDATED_COUNTRY = "BBB";

    private static final Integer DEFAULT_DURATION = 0;
    private static final Integer UPDATED_DURATION = 1;

    private static final String DEFAULT_LANGUAGE = "AA";
    private static final String UPDATED_LANGUAGE = "BB";

    @Autowired
    private TranscriptRepository transcriptRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTranscriptMockMvc;

    private Transcript transcript;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TranscriptResource transcriptResource = new TranscriptResource(transcriptRepository);
        this.restTranscriptMockMvc = MockMvcBuilders.standaloneSetup(transcriptResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transcript createEntity(EntityManager em) {
        Transcript transcript = new Transcript()
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .imdbId(DEFAULT_IMDB_ID)
            .coverUrl(DEFAULT_COVER_URL)
            .createdAt(DEFAULT_CREATED_AT)
            .description(DEFAULT_DESCRIPTION)
            .year(DEFAULT_YEAR)
            .genres(DEFAULT_GENRES)
            .country(DEFAULT_COUNTRY)
            .duration(DEFAULT_DURATION)
            .language(DEFAULT_LANGUAGE);
        return transcript;
    }

    @Before
    public void initTest() {
        transcript = createEntity(em);
    }

    @Test
    @Transactional
    public void createTranscript() throws Exception {
        int databaseSizeBeforeCreate = transcriptRepository.findAll().size();

        // Create the Transcript
        restTranscriptMockMvc.perform(post("/api/transcripts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transcript)))
            .andExpect(status().isCreated());

        // Validate the Transcript in the database
        List<Transcript> transcriptList = transcriptRepository.findAll();
        assertThat(transcriptList).hasSize(databaseSizeBeforeCreate + 1);
        Transcript testTranscript = transcriptList.get(transcriptList.size() - 1);
        assertThat(testTranscript.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTranscript.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testTranscript.getImdbId()).isEqualTo(DEFAULT_IMDB_ID);
        assertThat(testTranscript.getCoverUrl()).isEqualTo(DEFAULT_COVER_URL);
        assertThat(testTranscript.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testTranscript.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTranscript.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testTranscript.getGenres()).isEqualTo(DEFAULT_GENRES);
        assertThat(testTranscript.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testTranscript.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testTranscript.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    public void createTranscriptWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transcriptRepository.findAll().size();

        // Create the Transcript with an existing ID
        transcript.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTranscriptMockMvc.perform(post("/api/transcripts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transcript)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Transcript> transcriptList = transcriptRepository.findAll();
        assertThat(transcriptList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = transcriptRepository.findAll().size();
        // set the field null
        transcript.setName(null);

        // Create the Transcript, which fails.

        restTranscriptMockMvc.perform(post("/api/transcripts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transcript)))
            .andExpect(status().isBadRequest());

        List<Transcript> transcriptList = transcriptRepository.findAll();
        assertThat(transcriptList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = transcriptRepository.findAll().size();
        // set the field null
        transcript.setType(null);

        // Create the Transcript, which fails.

        restTranscriptMockMvc.perform(post("/api/transcripts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transcript)))
            .andExpect(status().isBadRequest());

        List<Transcript> transcriptList = transcriptRepository.findAll();
        assertThat(transcriptList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCoverUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = transcriptRepository.findAll().size();
        // set the field null
        transcript.setCoverUrl(null);

        // Create the Transcript, which fails.

        restTranscriptMockMvc.perform(post("/api/transcripts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transcript)))
            .andExpect(status().isBadRequest());

        List<Transcript> transcriptList = transcriptRepository.findAll();
        assertThat(transcriptList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = transcriptRepository.findAll().size();
        // set the field null
        transcript.setDescription(null);

        // Create the Transcript, which fails.

        restTranscriptMockMvc.perform(post("/api/transcripts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transcript)))
            .andExpect(status().isBadRequest());

        List<Transcript> transcriptList = transcriptRepository.findAll();
        assertThat(transcriptList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTranscripts() throws Exception {
        // Initialize the database
        transcriptRepository.saveAndFlush(transcript);

        // Get all the transcriptList
        restTranscriptMockMvc.perform(get("/api/transcripts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transcript.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].imdbId").value(hasItem(DEFAULT_IMDB_ID.toString())))
            .andExpect(jsonPath("$.[*].coverUrl").value(hasItem(DEFAULT_COVER_URL.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].genres").value(hasItem(DEFAULT_GENRES.toString())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION)))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }

    @Test
    @Transactional
    public void getTranscript() throws Exception {
        // Initialize the database
        transcriptRepository.saveAndFlush(transcript);

        // Get the transcript
        restTranscriptMockMvc.perform(get("/api/transcripts/{id}", transcript.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(transcript.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.imdbId").value(DEFAULT_IMDB_ID.toString()))
            .andExpect(jsonPath("$.coverUrl").value(DEFAULT_COVER_URL.toString()))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.genres").value(DEFAULT_GENRES.toString()))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTranscript() throws Exception {
        // Get the transcript
        restTranscriptMockMvc.perform(get("/api/transcripts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTranscript() throws Exception {
        // Initialize the database
        transcriptRepository.saveAndFlush(transcript);
        int databaseSizeBeforeUpdate = transcriptRepository.findAll().size();

        // Update the transcript
        Transcript updatedTranscript = transcriptRepository.findOne(transcript.getId());
        updatedTranscript
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .imdbId(UPDATED_IMDB_ID)
            .coverUrl(UPDATED_COVER_URL)
            .createdAt(UPDATED_CREATED_AT)
            .description(UPDATED_DESCRIPTION)
            .year(UPDATED_YEAR)
            .genres(UPDATED_GENRES)
            .country(UPDATED_COUNTRY)
            .duration(UPDATED_DURATION)
            .language(UPDATED_LANGUAGE);

        restTranscriptMockMvc.perform(put("/api/transcripts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTranscript)))
            .andExpect(status().isOk());

        // Validate the Transcript in the database
        List<Transcript> transcriptList = transcriptRepository.findAll();
        assertThat(transcriptList).hasSize(databaseSizeBeforeUpdate);
        Transcript testTranscript = transcriptList.get(transcriptList.size() - 1);
        assertThat(testTranscript.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTranscript.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTranscript.getImdbId()).isEqualTo(UPDATED_IMDB_ID);
        assertThat(testTranscript.getCoverUrl()).isEqualTo(UPDATED_COVER_URL);
        assertThat(testTranscript.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testTranscript.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTranscript.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testTranscript.getGenres()).isEqualTo(UPDATED_GENRES);
        assertThat(testTranscript.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testTranscript.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testTranscript.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    public void updateNonExistingTranscript() throws Exception {
        int databaseSizeBeforeUpdate = transcriptRepository.findAll().size();

        // Create the Transcript

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTranscriptMockMvc.perform(put("/api/transcripts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transcript)))
            .andExpect(status().isCreated());

        // Validate the Transcript in the database
        List<Transcript> transcriptList = transcriptRepository.findAll();
        assertThat(transcriptList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTranscript() throws Exception {
        // Initialize the database
        transcriptRepository.saveAndFlush(transcript);
        int databaseSizeBeforeDelete = transcriptRepository.findAll().size();

        // Get the transcript
        restTranscriptMockMvc.perform(delete("/api/transcripts/{id}", transcript.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Transcript> transcriptList = transcriptRepository.findAll();
        assertThat(transcriptList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Transcript.class);
        Transcript transcript1 = new Transcript();
        transcript1.setId(1L);
        Transcript transcript2 = new Transcript();
        transcript2.setId(transcript1.getId());
        assertThat(transcript1).isEqualTo(transcript2);
        transcript2.setId(2L);
        assertThat(transcript1).isNotEqualTo(transcript2);
        transcript1.setId(null);
        assertThat(transcript1).isNotEqualTo(transcript2);
    }
}
