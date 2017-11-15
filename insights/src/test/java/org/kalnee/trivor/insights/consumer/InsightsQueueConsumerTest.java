package org.kalnee.trivor.insights.consumer;

import org.junit.Test;
import org.kalnee.trivor.insights.domain.dto.SubtitleDTO;
import org.kalnee.trivor.insights.service.SubtitleService;

import static org.kalnee.trivor.insights.domain.dto.TypeEnum.MOVIE;
import static org.kalnee.trivor.insights.domain.dto.TypeEnum.TV_SHOW;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class InsightsQueueConsumerTest {

  private SubtitleService subtitleService = mock(SubtitleService.class);
  private InsightsQueueConsumer consumer = new InsightsQueueConsumer(subtitleService);
  private SubtitleDTO tvShow = new SubtitleDTO("aaabbb22", "tv show", 1, 1, 2010, 120, TV_SHOW);
  private SubtitleDTO movie = new SubtitleDTO("aaabbb22", "tv show", 2010, 120, MOVIE);

  @Test
  public void testCorrectURITVShow() {
    consumer.consume(tvShow);
    verify(subtitleService, times(1)).process(any(SubtitleDTO.class));
  }

  @Test
  public void testCorrectURIMovie() {
    consumer.consume(movie);
    verify(subtitleService, times(1)).process(any(SubtitleDTO.class));
  }
}
