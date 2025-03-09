package com.sujith.kotlin.stocksapp.domine.models

import com.squareup.moshi.Json

data class CompanyInfoModel(
    val description: String,
    val symbol: String,
    val name: String,
    val country: String,
    val industry: String,
)