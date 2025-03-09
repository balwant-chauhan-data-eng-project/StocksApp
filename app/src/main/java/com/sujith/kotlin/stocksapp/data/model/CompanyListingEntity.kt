package com.sujith.kotlin.stocksapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CompanyListingEntity(
    @PrimaryKey(autoGenerate = true) val id : Int? = null,
    val name: String,
    val symbol: String,
    val exchange: String,
    val assetType: String,
    val ipoDate: String,
)
