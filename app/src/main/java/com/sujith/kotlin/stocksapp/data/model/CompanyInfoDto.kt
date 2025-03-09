package com.sujith.kotlin.stocksapp.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CompanyInfoDto(
    @Json(name = "Symbol") val symbol: String?,
    @Json(name = "Description") val description: String?,
    @Json(name = "Name") val name: String?,
    @Json(name = "Country") val country: String?,
    @Json(name = "Industry") val industry: String?,
)
