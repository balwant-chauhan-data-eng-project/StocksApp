package com.sujith.kotlin.stocksapp.presentation.company_listings.viewmodel

sealed class CompanyListingsEvent {
    data object Refresh : CompanyListingsEvent()
    data class OnSearchQueryChange(val query: String) : CompanyListingsEvent()
}