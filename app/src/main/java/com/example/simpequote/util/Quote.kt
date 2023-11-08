package com.example.simpequote.util

data class Quote (
    val idQuote: String,
    val contentQuote: String,
    val authorQuote: String,
    val tagsQuote: List<String>,
    val authorSlug: String,
    val length: Long,
    val dateAdded: String,
    val dateModified: String
)