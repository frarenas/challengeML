package com.farenas.challengeml.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Paging(

    @Json(name = "limit")
    var limit: Int?,

    @Json(name = "offset")
    var offset: Int?,

    @Json(name = "primary_results")
    var primaryResults: Int?,

    @Json(name = "total")
    var total: Int
)