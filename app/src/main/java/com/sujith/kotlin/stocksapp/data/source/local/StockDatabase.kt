package com.sujith.kotlin.stocksapp.data.source.local

import android.util.Log
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.sujith.kotlin.stocksapp.data.model.CompanyListingEntity
import java.security.Principal

@Database(entities = [CompanyListingEntity::class], version = 2, exportSchema = false)
abstract class StockDatabase : RoomDatabase() {
    abstract fun getStockDao(): StockDao

    companion object {
        fun Migration_1_2() = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
//                database.execSQL("ALTER TABLE CompanyListingEntity ADD COLUMN symbol TEXT NOT NULL DEFAULT '")
                Log.d("test", "Migration_1_2: ")
            }
        }
    }
}