package org.kalnee.trivor.insights.consumer;

import org.kalnee.trivor.insights.domain.dto.SubtitleDTO;
import org.kalnee.trivor.insights.service.SubtitleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;

import javax.validation.Valid;

import static org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy.ON_SUCCESS;

@Component
public class InsightsQueueConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(InsightsQueueConsumer.class);

    private final SubtitleService subtitleService;

    @Autowired
    public InsightsQueueConsumer(SubtitleService subtitleService) {
        this.subtitleService = subtitleService;
    }

    @SqsListener(value = "${cloud.aws.queues.trivorInsights}", deletionPolicy = ON_SUCCESS)
    public synchronized void consume(@Valid SubtitleDTO subtitleDTO) {
        LOGGER.info("Message received: {}", subtitleDTO);
        subtitleService.process(subtitleDTO);
    }
}
