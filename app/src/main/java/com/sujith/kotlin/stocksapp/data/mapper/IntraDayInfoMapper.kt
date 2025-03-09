package com.sujith.kotlin.stocksapp.data.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.sujith.kotlin.stocksapp.data.model.IntraDayInfoDto
import com.sujith.kotlin.stocksapp.domine.models.IntraDayInfoModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
fun IntraDayInfoDto.toIntraDayInfoModel(): IntraDayInfoModel {
    val pattern = "yyyy-MM-dd HH:mm:ss"
    val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
    val localDateTime = LocalDateTime.parse(timestamp, formatter)
    return IntraDayInfoModel(
        date = localDateTime,
        open = open,
        high = high,
        low = low,
        close = close,
        volume = volume
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun IntraDayInfoModel.toIntraDayInfoDto(): IntraDayInfoDto {
    val pattern = "yyyy-MM-dd HH:mm:ss"
    return IntraDayInfoDto(
        timestamp = date.format(DateTimeFormatter.ofPattern(pattern, Locale.getDefault())),
        open = open,
        high = high,
        low = low,
        close = close,
        volume = volume
    )
}