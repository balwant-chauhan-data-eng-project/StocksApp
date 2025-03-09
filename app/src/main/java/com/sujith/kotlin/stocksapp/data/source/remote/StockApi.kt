package com.sujith.kotlin.stocksapp.data.source.remote

import com.sujith.kotlin.stocksapp.data.model.CompanyInfoDto
import com.sujith.kotlin.stocksapp.data.model.IntraDayInfoDto
import com.sujith.kotlin.stocksapp.data.model.IntraDayInfoResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface StockApi {

    companion object {
        const val BASE_URL = "https://alphavantage.co"
        const val API_KEY = "S0544TFBUNQQ8E8I"
    }

    @GET("query?function=LISTING_STATUS")
    suspend fun getListings(
        @Query("apikey") apikey: String = API_KEY,
    ): ResponseBody


    @GET("query?function=TIME_SERIES_INTRADAY&interval=5min")
    suspend fun getIntraDayInfo(
        @Query("apikey") apikey: String = API_KEY,
        @Query("symbol") symbol: String,
    ): ResponseBody

    @GET("query?function=TIME_SERIES_INTRADAY&interval=5min")
    suspend fun getIntraDayInfo1(
        @Query("apikey") apikey: String = API_KEY,
        @Query("symbol") symbol: String,
    ): Response<IntraDayInfoResponse>

    @GET("query?function=OVERVIEW")
    suspend fun getCompanyInfo(
        @Query("apikey") apikey: String = API_KEY,
        @Query("symbol") symbol: String,
    ): ResponseBody

    @GET("query?function=OVERVIEW")
    @Headers("Content-Type:application/json")
    suspend fun getCompanyInfo1(
        @Query("apikey") apikey: String = API_KEY,
        @Query("symbol") symbol: String,
    ): Response<CompanyInfoDto>
}