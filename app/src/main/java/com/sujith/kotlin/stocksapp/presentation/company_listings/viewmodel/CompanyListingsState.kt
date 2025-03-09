package com.sujith.kotlin.stocksapp.presentation.company_listings.viewmodel

import com.sujith.kotlin.stocksapp.domine.models.CompanyListing

data class CompanyListingsState(
    val companies : List<CompanyListing> = emptyList(),
    val isLoading : Boolean = false,
    val isRefreshing : Boolean = false,
    val searchQuery : String = "",
    val errorMsg : String = ""
)