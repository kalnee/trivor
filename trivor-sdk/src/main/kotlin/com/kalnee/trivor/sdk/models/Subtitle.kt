package com.kalnee.trivor.sdk.models

class Subtitle(var sentences: List<Sentence>?) {

    var name: String? = null
    var type: TypeEnum? = null
    var season: Int? = null
    var episode: Int? = null
    var duration: Int? = null
}
