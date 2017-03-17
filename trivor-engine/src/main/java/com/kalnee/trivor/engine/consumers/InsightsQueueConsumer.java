package com.kalnee.trivor.engine.consumers;

import static com.kalnee.trivor.engine.dto.TypeEnum.TV_SHOW;
import static java.lang.String.format;
import static org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy.ON_SUCCESS;

import java.net.URI;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.config.annotation.NotificationMessage;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;

import com.kalnee.trivor.engine.dto.SubtitleDTO;
import com.kalnee.trivor.engine.insights.processors.SubtitleProcessor;

@Component
public class InsightsQueueConsumer {

  private static final Logger LOGGER = LoggerFactory.getLogger(InsightsQueueConsumer.class);
  private static final String TV_SHOW_FILENAME = "%s-S%dE%d.srt";
  private static final String MOVIE_FILENAME = "%s.srt";
  private static final String S3_URI = "s3://%s/%s";

  private final SubtitleProcessor subtitleProcessor;
  private final String bucket;

  @Autowired
  public InsightsQueueConsumer(SubtitleProcessor subtitleProcessor,
      												 @Value("${cloud.aws.buckets.trivorSubtitles}") String bucket) {
    this.subtitleProcessor = subtitleProcessor;
    this.bucket = bucket;
  }

  @SqsListener(value = "${cloud.aws.queues.trivorInsights}", deletionPolicy = ON_SUCCESS)
  public void consume(@Valid SubtitleDTO subtitle) {
    LOGGER.info("Message received: {}", subtitle);

    String filename;

    if (TV_SHOW.equals(subtitle.getType())) {
      filename = format(
      	TV_SHOW_FILENAME, subtitle.getImdbId(), subtitle.getSeason(), subtitle.getEpisode()
			);
    } else {
      filename = format(MOVIE_FILENAME, subtitle.getImdbId());
    }

    final URI uri = URI.create(format(S3_URI, bucket, filename));
    LOGGER.info("Storage URI: {}", uri);

    subtitleProcessor.process(uri, subtitle);
  }

}
