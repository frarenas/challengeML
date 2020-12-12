package com.farenas.challengeml.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchResult(

    @Json(name = "paging")
    var paging: Paging,

    @Json(name = "results")
    var products: List<Product>
)