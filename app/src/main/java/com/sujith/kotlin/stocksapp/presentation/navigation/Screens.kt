package com.sujith.kotlin.stocksapp.presentation.navigation

import androidx.compose.ui.Modifier
import kotlinx.serialization.Serializable

sealed class Screens {
    @Serializable
    data object CompanyListingScreen : Screens()

    @Serializable
    data class CompanyInfoScreen(val symbol: String) : Screens()
//    data object StockChartScreen : Screens()
}