package org.kalnee.trivor.insights.consumers;

import org.junit.Test;
import org.kalnee.trivor.insights.dto.SubtitleDTO;
import org.kalnee.trivor.insights.services.SubtitleService;
import org.mockito.ArgumentCaptor;

import java.net.URI;

import static org.junit.Assert.assertEquals;
import static org.kalnee.trivor.insights.dto.TypeEnum.MOVIE;
import static org.kalnee.trivor.insights.dto.TypeEnum.TV_SHOW;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class InsightsQueueConsumerTest {

  private SubtitleService subtitleService = mock(SubtitleService.class);
  private InsightsQueueConsumer consumer = new InsightsQueueConsumer(subtitleService, "bucket");
  private SubtitleDTO tvShow = new SubtitleDTO("aaabbb22", "tv show", 1, 1, 2010, 120, TV_SHOW);
  private SubtitleDTO movie = new SubtitleDTO("aaabbb22", "tv show", 2010, 120, MOVIE);

  @Test
  public void testCorrectURITVShow() {
    ArgumentCaptor<URI> uriCaptor = ArgumentCaptor.forClass(URI.class);
    ArgumentCaptor<SubtitleDTO> subtitleCaptor = ArgumentCaptor.forClass(SubtitleDTO.class);

    consumer.consume(tvShow);

    verify(subtitleService, times(1)).process(any(URI.class), any(SubtitleDTO.class));
    verify(subtitleService).process(uriCaptor.capture(), subtitleCaptor.capture());
    assertEquals("s3://bucket/aaabbb22-S1E1.srt", uriCaptor.getValue().toString());
  }

  @Test
  public void testCorrectURIMovie() {
    ArgumentCaptor<URI> uriCaptor = ArgumentCaptor.forClass(URI.class);
    ArgumentCaptor<SubtitleDTO> subtitleCaptor = ArgumentCaptor.forClass(SubtitleDTO.class);

    consumer.consume(movie);

    verify(subtitleService, times(1)).process(any(URI.class), any(SubtitleDTO.class));
    verify(subtitleService).process(uriCaptor.capture(), subtitleCaptor.capture());
    assertEquals("s3://bucket/aaabbb22.srt", uriCaptor.getValue().toString());
  }
}
