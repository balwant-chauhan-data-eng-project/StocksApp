package com.sujith.kotlin.stocksapp.data.csv

import com.opencsv.CSVReader
import com.sujith.kotlin.stocksapp.domine.models.CompanyInfoModel
import com.sujith.kotlin.stocksapp.domine.models.CompanyListing
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import javax.inject.Inject

class CompanyInfoParser @Inject constructor() : CSVParse<CompanyInfoModel> {
    override suspend fun parse(stream: InputStream): List<CompanyInfoModel> {
        val csvReader = CSVReader(InputStreamReader(stream))
        return withContext(Dispatchers.IO) {
            csvReader.readAll()
                .drop(1)
                .distinct()
                .mapNotNull {
                    val symbol = it.getOrNull(0)
                    val name = it.getOrNull(2)
                    val description = it.getOrNull(3)
                    val country = it.getOrNull(7)
                    val industry = it.getOrNull(9)
                    CompanyInfoModel(
                        name = name ?: return@mapNotNull null,
                        symbol = symbol ?: return@mapNotNull null,
                        description = description ?: return@mapNotNull null,
                        country = country ?: return@mapNotNull null,
                        industry = industry ?: return@mapNotNull null
                    )
                }
                .sortedBy {
                    it.name.lowercase()
                }
                .also {
                    csvReader.close()
                }
        }
    }
}