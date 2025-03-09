package com.sujith.kotlin.stocksapp.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import coil3.network.HttpException
import com.sujith.kotlin.stocksapp.data.csv.CSVParse
import com.sujith.kotlin.stocksapp.data.mapper.toCompanyInfoModel
import com.sujith.kotlin.stocksapp.data.mapper.toCompanyListing
import com.sujith.kotlin.stocksapp.data.mapper.toCompanyListingEntity
import com.sujith.kotlin.stocksapp.data.mapper.toIntraDayInfoModel
import com.sujith.kotlin.stocksapp.data.model.IntraDayInfoDto
import com.sujith.kotlin.stocksapp.data.source.local.StockDatabase
import com.sujith.kotlin.stocksapp.data.source.remote.StockApi
import com.sujith.kotlin.stocksapp.di.AppModule.moshi
import com.sujith.kotlin.stocksapp.domine.models.CompanyInfoModel
import com.sujith.kotlin.stocksapp.domine.models.CompanyListing
import com.sujith.kotlin.stocksapp.domine.models.IntraDayInfoModel
import com.sujith.kotlin.stocksapp.domine.repository.StockRepository
import com.sujith.kotlin.stocksapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImp @Inject constructor(
    private val stockApi: StockApi,
    private val database: StockDatabase,
    private val companyListingsParser: CSVParse<CompanyListing>,
    private val intraDayInfoParser: CSVParse<IntraDayInfoModel>,
    private val companyInfoParser: CSVParse<CompanyInfoModel>,
) : StockRepository {


    override suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String,
    ): Flow<Resource<List<CompanyListing>>> {
        return flow {
            emit(Resource.Loading(true))
            val localListings = database.getStockDao().searchCompanyListing(query)
            emit(Resource.Success(
                data = localListings.map { it.toCompanyListing() }
            ))
            val isDbEmpty = localListings.isEmpty() && query.isBlank()
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote
            if (shouldJustLoadFromCache) {
                emit(Resource.Loading(true))
                val remoteListings = try {
                    val response = stockApi.getListings()
                    companyListingsParser.parse(response.byteStream())
                } catch (e: IOException) {
                    e.printStackTrace()
                    emit(Resource.Error("Couldn't load data"))
                    null
                } catch (e: HttpException) {
                    e.printStackTrace()
                    emit(Resource.Error("Couldn't load data"))
                    null
                } catch (e: Exception) {
                    e.printStackTrace()
                    emit(Resource.Error("Couldn't load data"))
                    null
                }

                remoteListings?.let { listings ->
                    database.getStockDao().clearCompanyListings()
                    database.getStockDao().insertCompanyListings(
                        listings.map { it.toCompanyListingEntity() }
                    )
                    emit(Resource.Success(listings))
                    emit(Resource.Success(
                        data = database.getStockDao().searchCompanyListing("")
                            .map { it.toCompanyListing() }
                    ))
                    emit(Resource.Loading(false))
                }
            }
        }
    }

    override suspend fun getCompanyInfo(symbol: String): Resource<CompanyInfoModel> {
        return try {
//            val response = stockApi.getCompanyInfo(symbol = symbol)
//            val result = companyInfoParser.parse(response.byteStream())
//            if (result.isEmpty()) {
//                return Resource.Error("Couldn't load data")
//            }
//            Resource.Success(result.first())

            val response = stockApi.getCompanyInfo1(symbol = symbol)
            Resource.Success(response.body()?.toCompanyInfoModel() ?: null)

        } catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error("Couldn't load data")
        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error("Couldn't load data")
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error("Couldn't load data ${e.message}")
        }
    }


//    val response = stockApi.getIntraDayInfo(symbol = symbol)
//            val result = intraDayInfoParser.parse(response.byteStream())
//            Resource.Success(result)

    //            val res = stockApi.getIntraDayInfo1(symbol = symbol)
//            var result: List<IntraDayInfoDto>? = null
//            if (res.isSuccessful && res.body() != null && res.code() == 200 && res.body()!!
//                    .isNotEmpty()
//            ) {
//                result = res.body()
//            }
//            Resource.Success(result!!.map { it.toIntraDayInfoModel() })
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getIntraDayInfo(symbol: String): Resource<List<IntraDayInfoModel>> {
        return try {
            val res = stockApi.getIntraDayInfo1(symbol = symbol)

            if (res.isSuccessful && res.body() != null) {
                val responseBody = res.body()!!

                // Directly access the "Time Series (5min)" data
                val timeSeriesData = responseBody.timeSeries

                if (timeSeriesData.isNullOrEmpty()) {
                    return Resource.Error("Time series data not found")
                }

                val result = timeSeriesData.map { (timestamp, values) ->
                    IntraDayInfoDto(
                        timestamp = timestamp,
                        open = values["1. open"]?.toDoubleOrNull() ?: 0.0,
                        high = values["2. high"]?.toDoubleOrNull() ?: 0.0,
                        low = values["3. low"]?.toDoubleOrNull() ?: 0.0,
                        close = values["4. close"]?.toDoubleOrNull() ?: 0.0,
                        volume = values["5. volume"]?.toLongOrNull() ?: 0L
                    )
                }

                Resource.Success(result.map { it.toIntraDayInfoModel() })
            } else {
                Resource.Error("Failed to fetch intraday info")
            }
        } catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error("Couldn't load data")
        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error("Couldn't load data")
        }
    }

}