package com.sujith.kotlin.stocksapp.di

import com.sujith.kotlin.stocksapp.data.csv.CSVParse
import com.sujith.kotlin.stocksapp.data.csv.CompanyInfoParser
import com.sujith.kotlin.stocksapp.data.csv.CompanyListingsParser
import com.sujith.kotlin.stocksapp.data.csv.IntraDayInfoParser
import com.sujith.kotlin.stocksapp.data.repository.StockRepositoryImp
import com.sujith.kotlin.stocksapp.domine.models.CompanyInfoModel
import com.sujith.kotlin.stocksapp.domine.models.CompanyListing
import com.sujith.kotlin.stocksapp.domine.models.IntraDayInfoModel
import com.sujith.kotlin.stocksapp.domine.repository.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCompanyListingsParser(
        companyListingsParser: CompanyListingsParser,
    ): CSVParse<CompanyListing>


    @Binds
    @Singleton
    abstract fun bindStockRepository(
        stockRepositoryImpl: StockRepositoryImp,
    ): StockRepository

    @Binds
    @Singleton
    abstract fun bindIntraDayInfoParser(
        intraDayInfoParser: IntraDayInfoParser,
    ): CSVParse<IntraDayInfoModel>

    @Binds
    @Singleton
    abstract fun bindCompanyInfoParser(
        companyInfoParser: CompanyInfoParser,
    ): CSVParse<CompanyInfoModel>
}