package com.sujith.kotlin.stocksapp.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.sujith.kotlin.stocksapp.presentation.company_info.ui.CompanyInfoScreen
import com.sujith.kotlin.stocksapp.presentation.company_listings.ui.CompanyListingScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = Screens.CompanyListingScreen
    ) {
        composable<Screens.CompanyListingScreen> {
            CompanyListingScreen(
                modifier = modifier,
                navigation = {
                    navController.navigate(
                        Screens.CompanyInfoScreen(
                            symbol = it
                        )
                    )
                },
            )
        }
        composable<Screens.CompanyInfoScreen> { navBackStackEntry ->
            val screen = navBackStackEntry.toRoute<Screens.CompanyInfoScreen>()
            val sysmbol = screen.symbol
            CompanyInfoScreen(symbol = sysmbol, modifier = modifier)
        }
    }
}