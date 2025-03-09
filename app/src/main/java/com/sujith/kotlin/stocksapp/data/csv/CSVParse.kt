package com.sujith.kotlin.stocksapp.data.csv

import java.io.InputStream

interface CSVParse<T> {
    suspend fun parse(stream: InputStream): List<T>
}