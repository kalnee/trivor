package com.kalnee.trivor.sdk;

import com.kalnee.trivor.sdk.insights.processors.SubtitleProcessor;

import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) throws URISyntaxException {
        SubtitleProcessor sp = new SubtitleProcessor
                .Builder(Main.class.getResource("/language/s1e1.srt").toURI())
                .withDuration(43)
                .build();
        System.out.println("Rate of Speech: " + sp.getInsights().get("rate-of-speech"));
    }

}
