package com.sujith.kotlin.stocksapp.data.csv

import android.os.Build
import androidx.annotation.RequiresApi
import com.opencsv.CSVReader
import com.sujith.kotlin.stocksapp.data.mapper.toIntraDayInfoModel
import com.sujith.kotlin.stocksapp.data.model.IntraDayInfoDto
import com.sujith.kotlin.stocksapp.domine.models.CompanyListing
import com.sujith.kotlin.stocksapp.domine.models.IntraDayInfoModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import java.time.LocalDateTime
import javax.inject.Inject

class IntraDayInfoParser @Inject constructor() : CSVParse<IntraDayInfoModel> {
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun parse(stream: InputStream): List<IntraDayInfoModel> {
        val csvReader = CSVReader(InputStreamReader(stream))
        return withContext(Dispatchers.IO) {
            csvReader.readAll()
                .drop(1)
                .distinct()
                .mapNotNull {
                    val timestamp = it.getOrNull(0) ?: return@mapNotNull null
                    val open = it.getOrNull(1) ?: return@mapNotNull null
                    val high = it.getOrNull(2) ?: return@mapNotNull null
                    val low = it.getOrNull(3) ?: return@mapNotNull null
                    val close = it.getOrNull(4) ?: return@mapNotNull null
                    val volume = it.getOrNull(5) ?: return@mapNotNull null
                    val dto = IntraDayInfoDto(
                        timestamp = timestamp,
                        open = open.toDouble(),
                        high = high.toDouble(),
                        low = low.toDouble(),
                        close = close.toDouble(),
                        volume = volume.toLong()
                    )
                    dto.toIntraDayInfoModel()
                }
                .filter {
                    it.date.dayOfMonth == LocalDateTime.now().minusDays(1).dayOfMonth
                }
                .sortedBy {
                    it.date.hour
                }
                .also {
                    csvReader.close()
                }
        }
    }
}