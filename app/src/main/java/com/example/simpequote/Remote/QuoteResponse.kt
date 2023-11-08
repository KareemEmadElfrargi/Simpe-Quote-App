package com.example.simpequote.Remote

import com.google.gson.annotations.SerializedName

data class QuoteResponse (
    @SerializedName("_id") val idQuote :String,
    @SerializedName("content") val contentQuote :String,
    @SerializedName("author") val authorQuote :String,
    @SerializedName("tags") val tagsQuote :List<String>,
    val authorSlug :String,
    val length :Long,
    val dateAdded :String,
    val dateModified :String,
)