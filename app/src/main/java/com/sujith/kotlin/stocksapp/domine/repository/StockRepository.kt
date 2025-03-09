package com.sujith.kotlin.stocksapp.domine.repository


import com.sujith.kotlin.stocksapp.domine.models.CompanyInfoModel
import com.sujith.kotlin.stocksapp.domine.models.CompanyListing
import com.sujith.kotlin.stocksapp.domine.models.IntraDayInfoModel
import com.sujith.kotlin.stocksapp.util.Resource
import kotlinx.coroutines.flow.Flow


interface StockRepository {
    suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String,
    ): Flow<Resource<List<CompanyListing>>>

    suspend fun getCompanyInfo(
        symbol: String,
    ): Resource<CompanyInfoModel>

    suspend fun getIntraDayInfo(
        symbol: String,
    ): Resource<List<IntraDayInfoModel>>
}