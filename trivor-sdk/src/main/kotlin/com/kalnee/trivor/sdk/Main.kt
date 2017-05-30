package com.kalnee.trivor.sdk

import com.kalnee.trivor.sdk.insights.processors.SubtitleProcessor

class Main

fun main(args: Array<String>) {
    val uri = Main::class.java.getResource("/language/s1e1.srt").toURI()
    val sp = SubtitleProcessor.Builder(uri).build()
    println(sp.insights[0])
}
