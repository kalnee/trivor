package com.kalnee.trivor.sdk.models

import org.apache.commons.lang3.StringUtils.SPACE

class Sentence {

    var sentence: String? = null
    var tokens: List<Token>? = null

    constructor() {}

    constructor(sentence: String, tokens: List<Token>) {
        this.sentence = sentence
        this.tokens = tokens
    }

    fun getSentenceTags(): String {
        return tokens!!.map { Token::tag }.joinToString(SPACE)
    }

    override fun toString(): String {
        return "Sentence{sentence='$sentence', tokens=$tokens}"
    }
}
