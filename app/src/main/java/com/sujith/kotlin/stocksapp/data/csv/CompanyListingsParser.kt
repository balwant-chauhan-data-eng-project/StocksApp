package com.sujith.kotlin.stocksapp.data.csv

import com.opencsv.CSVReader
import com.sujith.kotlin.stocksapp.domine.models.CompanyListing
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import javax.inject.Inject

class CompanyListingsParser @Inject constructor(): CSVParse<CompanyListing> {
    override suspend fun parse(stream: InputStream): List<CompanyListing> {
        val csvReader = CSVReader(InputStreamReader(stream))
        return withContext(Dispatchers.IO){
            csvReader.readAll()
                .drop(1)
                .distinct()
                .mapNotNull {
                    val symbol = it.getOrNull(0)
                    val name = it.getOrNull(1)
                    val exchange = it.getOrNull(2)
                    val assetType = it.getOrNull(3)
                    val ipoDate = it.getOrNull(4)
                    CompanyListing(
                        name = name ?: return@mapNotNull null,
                        symbol = symbol ?: return@mapNotNull null,
                        exchange = exchange ?: return@mapNotNull null,
                        assetType = assetType ?: return@mapNotNull null,
                        ipoDate = ipoDate ?: return@mapNotNull null
                    )
                }
                .also{
                    csvReader.close()
                }
        }
    }
}