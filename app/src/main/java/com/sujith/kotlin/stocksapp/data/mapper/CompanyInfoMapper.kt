package com.sujith.kotlin.stocksapp.data.mapper

import com.sujith.kotlin.stocksapp.data.model.CompanyInfoDto
import com.sujith.kotlin.stocksapp.domine.models.CompanyInfoModel

fun CompanyInfoDto.toCompanyInfoModel(): CompanyInfoModel {
    return CompanyInfoModel(
        description = description ?: "",
        symbol = symbol ?: "",
        name = name ?: "",
        country = country ?: "",
        industry = industry ?: ""
    )
}

fun CompanyInfoModel.toCompanyInfoDto(): CompanyInfoDto {
    return CompanyInfoDto(
        description = description ?: "",
        symbol = symbol ?: "",
        name = name ?: "",
        country = country ?: "",
        industry = industry ?: ""
    )
}