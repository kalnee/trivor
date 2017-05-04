package com.kalnee.trivor.engine.consumers;

import static com.kalnee.trivor.engine.dto.TypeEnum.MOVIE;
import static com.kalnee.trivor.engine.dto.TypeEnum.TV_SHOW;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.net.URI;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.kalnee.trivor.engine.dto.SubtitleDTO;
import com.kalnee.trivor.engine.insights.processors.SubtitleProcessor;

public class InsightsQueueConsumerTest {

  private SubtitleProcessor subtitleProcessor = mock(SubtitleProcessor.class);
  private InsightsQueueConsumer consumer = new InsightsQueueConsumer(subtitleProcessor, "bucket");
  private SubtitleDTO tvShow = new SubtitleDTO("aaabbb22", "tv show", 1, 1, 2010, TV_SHOW);
  private SubtitleDTO movie = new SubtitleDTO("aaabbb22", "tv show", 2010, MOVIE);

  @Test
  public void testCorrectURITVShow() {
    ArgumentCaptor<URI> uriCaptor = ArgumentCaptor.forClass(URI.class);
    ArgumentCaptor<SubtitleDTO> subtitleCaptor = ArgumentCaptor.forClass(SubtitleDTO.class);

    consumer.consume(tvShow);

    verify(subtitleProcessor, times(1)).process(any(URI.class), any(SubtitleDTO.class));
    verify(subtitleProcessor).process(uriCaptor.capture(), subtitleCaptor.capture());
    assertEquals("s3://bucket/aaabbb22-S1E1.srt", uriCaptor.getValue().toString());
  }

  @Test
  public void testCorrectURIMovie() {
    ArgumentCaptor<URI> uriCaptor = ArgumentCaptor.forClass(URI.class);
    ArgumentCaptor<SubtitleDTO> subtitleCaptor = ArgumentCaptor.forClass(SubtitleDTO.class);

    consumer.consume(movie);

    verify(subtitleProcessor, times(1)).process(any(URI.class), any(SubtitleDTO.class));
    verify(subtitleProcessor).process(uriCaptor.capture(), subtitleCaptor.capture());
    assertEquals("s3://bucket/aaabbb22.srt", uriCaptor.getValue().toString());
  }
}
