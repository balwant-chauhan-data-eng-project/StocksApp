package com.sujith.kotlin.stocksapp.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.sujith.kotlin.stocksapp.data.model.CompanyListingEntity

@Dao
interface StockDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompanyListings(
        companyListingEntities: List<CompanyListingEntity>,
    )

    @Query("DELETE FROM companylistingentity")
    suspend fun clearCompanyListings()

    @Query(
        """
    SELECT * FROM companylistingentity 
    WHERE LOWER(name) LIKE '%' || LOWER(:query) || '%'
       OR LOWER(symbol) LIKE '%' || LOWER(:query) || '%' 
       """
    )
    suspend fun searchCompanyListing(query: String): List<CompanyListingEntity>

}