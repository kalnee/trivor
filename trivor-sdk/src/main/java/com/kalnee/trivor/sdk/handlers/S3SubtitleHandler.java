package com.kalnee.trivor.sdk.handlers;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3URI;
import com.amazonaws.services.s3.model.S3Object;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.stream.Stream;

class S3SubtitleHandler implements SubtitleHandler {

	private static final Logger LOGGER = LogManager.getLogger(FileSubtitleHandler.class);

	static final String S3_SCHEME = "s3";

	private final URI uri;
	private final AmazonS3 s3Client;

	S3SubtitleHandler(URI uri) {
		this.uri = uri;
		this.s3Client = new AmazonS3Client();
	}

	@Override
	public Stream<String> lines() {
		LOGGER.debug("starting download from {}", uri);
		AmazonS3URI s3URI = new AmazonS3URI(uri);
		S3Object s3Object = s3Client.getObject(s3URI.getBucket(), s3URI.getKey());

		InputStream stream = s3Object.getObjectContent();
		return new BufferedReader(new InputStreamReader(stream)).lines();
	}
}
