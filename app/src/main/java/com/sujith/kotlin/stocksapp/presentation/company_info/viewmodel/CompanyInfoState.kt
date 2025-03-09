package com.sujith.kotlin.stocksapp.presentation.company_info.viewmodel

import com.sujith.kotlin.stocksapp.domine.models.CompanyInfoModel
import com.sujith.kotlin.stocksapp.domine.models.IntraDayInfoModel

data class CompanyInfoState(
    val stockInfo: List<IntraDayInfoModel> = emptyList(),
    val company: CompanyInfoModel? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)