package com.kalnee.trivor.sdk.models

class Insight<T> {

    var code: String? = null
    var value: T? = null

    constructor() {}

    constructor(code: String, value: T) {
        this.code = code
        this.value = value
    }

    override fun toString(): String {
        return "Insight{" +
                "code='" + code + '\'' +
                ", value=" + value +
                '}'
    }
}
