package com.sujith.kotlin.stocksapp.data.mapper

import com.sujith.kotlin.stocksapp.data.model.CompanyListingEntity
import com.sujith.kotlin.stocksapp.domine.models.CompanyListing


fun CompanyListingEntity.toCompanyListing() : CompanyListing{
    return CompanyListing(
        name = name,
        symbol = symbol,
        exchange = exchange,
        assetType = assetType,
        ipoDate = ipoDate,
    )
}


fun CompanyListing.toCompanyListingEntity() : CompanyListingEntity{
    return CompanyListingEntity(
        name = name,
        symbol = symbol,
        exchange = exchange,
        assetType = assetType,
        ipoDate = ipoDate,
    )
}