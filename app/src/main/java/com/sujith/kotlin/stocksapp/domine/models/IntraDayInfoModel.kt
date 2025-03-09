package com.sujith.kotlin.stocksapp.domine.models

import java.time.LocalDateTime
import java.util.Date

data class IntraDayInfoModel(
    val date: LocalDateTime,
    val open: Double,
    val high: Double,
    val low: Double,
    val close: Double,
    val volume: Long,
)