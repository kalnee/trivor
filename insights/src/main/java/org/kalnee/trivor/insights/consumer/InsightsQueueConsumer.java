package org.kalnee.trivor.insights.consumer;

import org.kalnee.trivor.insights.domain.dto.SubtitleDTO;
import org.kalnee.trivor.insights.service.SubtitleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.net.URI;

import static java.lang.String.format;
import static org.kalnee.trivor.insights.domain.dto.TypeEnum.TV_SHOW;
import static org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy.ON_SUCCESS;

@Component
public class InsightsQueueConsumer {

  private static final Logger LOGGER = LoggerFactory.getLogger(InsightsQueueConsumer.class);
  private static final String TV_SHOW_FILENAME = "%s-S%dE%d.srt";
  private static final String MOVIE_FILENAME = "%s.srt";
  private static final String S3_URI = "s3://%s/%s";

  private final SubtitleService subtitleService;
  private final String bucket;

  @Autowired
  public InsightsQueueConsumer(SubtitleService subtitleService,
                               @Value("${cloud.aws.buckets.trivorSubtitles}") String bucket) {
    this.subtitleService = subtitleService;
    this.bucket = bucket;
  }

  @SqsListener(value = "${cloud.aws.queues.trivorInsights}", deletionPolicy = ON_SUCCESS)
  public synchronized void consume(@Valid SubtitleDTO subtitleDTO) {
    LOGGER.info("Message received: {}", subtitleDTO);

    String filename;

    if (TV_SHOW.equals(subtitleDTO.getType())) {
      filename = format(
              TV_SHOW_FILENAME, subtitleDTO.getImdbId(), subtitleDTO.getSeason(), subtitleDTO.getEpisode()
      );
    } else {
      filename = format(MOVIE_FILENAME, subtitleDTO.getImdbId());
    }

    final URI uri = URI.create(format(S3_URI, bucket, filename));
    LOGGER.info("Storage URI: {}", uri);

    subtitleService.process(uri, subtitleDTO);
  }

}
