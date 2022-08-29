package com.udacity.asteroidradar

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DailyPicture(
    val date: String,
    @Json(name = "media_type")
    val mediaType: String,
    val title: String,
    val url: String,
    val explanation: String
)