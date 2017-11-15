package org.kalnee.trivor.insights.service;

import org.kalnee.trivor.insights.domain.Transcript;
import org.kalnee.trivor.insights.domain.dto.TranscriptDTO;
import org.kalnee.trivor.insights.domain.insights.Insights;
import org.kalnee.trivor.insights.repository.InsightsRepository;
import org.kalnee.trivor.insights.repository.TranscriptRepository;
import org.kalnee.trivor.nlp.insights.processors.TranscriptProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static org.apache.commons.lang3.StringUtils.EMPTY;

@Service
public class TranscriptService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TranscriptService.class);
    private static final String FILENAME = "tr-%s-%s";
    private static final String S3_URI = "s3://%s/%s";
    private static final String TRANSCRIPT_MINUTES_REGEX = "^.+:\\s*";
    private static final String TRANSCRIPT_CC_REGEX = "\\([A-Za-z,\\s]+\\)";

    private final TranscriptRepository transcriptRepository;
    private final InsightsRepository insightsRepository;
    private final String bucket;

    @Autowired
    public TranscriptService(TranscriptRepository transcriptRepository,
                             InsightsRepository insightsRepository,
                             @Value("${cloud.aws.buckets.trivorStorage}") String bucket) {
        this.transcriptRepository = transcriptRepository;
        this.insightsRepository = insightsRepository;
        this.bucket = bucket;
    }

    public void process(TranscriptDTO transcriptDTO) {
        deleteIfExists(transcriptDTO);

        final URI uri = URI.create(format(S3_URI, bucket, getFileName(transcriptDTO)));
        LOGGER.info("Storage URI: {}", uri);

        final TranscriptProcessor subtitleProcessor = new TranscriptProcessor.Builder(uri)
            .withMappers(asList(
                line -> line.replaceAll(TRANSCRIPT_MINUTES_REGEX, EMPTY),
                line -> line.replaceAll(TRANSCRIPT_CC_REGEX, EMPTY)
            )).build();

        final Transcript transcript = transcriptRepository.save(
            new Transcript(transcriptDTO, subtitleProcessor.getTranscript().getSentences())
        );

        LOGGER.info("Transcript created successfully.");

        insightsRepository.save(
            new Insights(transcript.getExternalId(), transcript.getId(), subtitleProcessor.getResult())
        );

        LOGGER.info("Insights created successfully.");
    }

    private String getFileName(TranscriptDTO transcriptDTO) {
        return format(FILENAME, transcriptDTO.getExternalId(), transcriptDTO.getName());
    }

    private void deleteIfExists(TranscriptDTO transcriptDTO) {
        transcriptRepository.findByExternalIdAndName(transcriptDTO.getExternalId(), transcriptDTO.getName())
            .forEach(t -> {
                transcriptRepository.delete(t);
                insightsRepository.delete(
                    insightsRepository.findAllByImdbIdAndSubtitleId(transcriptDTO.getExternalId(), t.getId())
                );
            });
    }
}
