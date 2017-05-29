package com.kalnee.trivor.sdk

fun main(args: Array<String>) {
    println("trivor-sdk")

    // Input: InputStream, URI (file://, s3://, http://)
    // Flow: pre-process - identify sentences, tokens and tags - run insights generators
    // Output: Generic response? individual methods to get particular insights? Process each one individually?

//    InsightProcessor ip = InsightsProcessor.Builder("s3://bucket/key").build();
//    ip.isProcessed()
//    ip.getPresentTense()
//    ip.getFrequentNouns()
//    ip.getCustomInsight(new MyInsightGenerator());
}
