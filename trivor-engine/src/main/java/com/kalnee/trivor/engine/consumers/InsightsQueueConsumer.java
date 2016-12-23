package com.kalnee.trivor.engine.consumers;

import static org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy.ON_SUCCESS;

import com.kalnee.trivor.engine.repositories.SubtitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;

@Component
public class InsightsQueueConsumer {

	private final SubtitleRepository subtitleRepository;

	@Autowired
	public InsightsQueueConsumer(SubtitleRepository subtitleRepository) {
		this.subtitleRepository = subtitleRepository;
	}

	@SqsListener(value = "${sqs.queue.insights}", deletionPolicy = ON_SUCCESS)
	public void receiveMessage(String raw) {
		System.out.println("Message received: " + subtitleRepository.findByYear(Integer.valueOf(raw)));
	}

}
