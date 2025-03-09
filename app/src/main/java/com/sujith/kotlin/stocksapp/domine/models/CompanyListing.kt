package com.sujith.kotlin.stocksapp.domine.models

data class CompanyListing(
    val name: String,
    val symbol: String,
    val exchange: String,
    val assetType: String,
    val ipoDate: String,
)
