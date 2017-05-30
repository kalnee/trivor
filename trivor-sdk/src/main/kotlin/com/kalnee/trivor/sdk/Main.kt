package com.kalnee.trivor.sdk

import com.kalnee.trivor.sdk.insights.processors.SubtitleProcessor

class Main

fun main(args: Array<String>) {
    val sp = SubtitleProcessor.Builder(Main::class.java.getResource("/language/s1e1.srt").toURI()).build()
    println(sp.insights)
}
