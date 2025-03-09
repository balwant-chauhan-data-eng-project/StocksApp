package com.sujith.kotlin.stocksapp.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class IntraDayInfoResponse(
    @Json(name = "Time Series (5min)")
    val timeSeries: Map<String, Map<String, String>>?, // Directly map "Time Series (5min)"
)


@JsonClass(generateAdapter = true)
data class IntraDayInfoDto(
    val timestamp: String,
    @Json(name = "1. open") val open: Double,
    @Json(name = "2. high") val high: Double,
    @Json(name = "3. low") val low: Double,
    @Json(name = "4. close") val close: Double,
    @Json(name = "5. volume") val volume: Long,
)
