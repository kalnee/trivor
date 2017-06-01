package com.kalnee.trivor.sdk;

import com.kalnee.trivor.sdk.insights.processors.SubtitleProcessor;

import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) throws URISyntaxException {
        SubtitleProcessor sp = new SubtitleProcessor
                .Builder(Main.class.getResource("/language/s1e1.srt").toURI()).build();
        System.out.println(sp.getInsights());
    }
}