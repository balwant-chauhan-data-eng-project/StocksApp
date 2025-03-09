package com.sujith.kotlin.stocksapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sujith.kotlin.stocksapp.presentation.navigation.AppNavigation
import com.sujith.kotlin.stocksapp.ui.theme.StocksAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StocksAppTheme {
                val navHostController = rememberNavController()

                Scaffold(
                    topBar = {
//                        TopAppBar(
//                            title = {
//                                Text(text = "Stocks App")
//                            }
//                        )
                        DynamicTopAppBar(navHostController)
                    },
                    modifier = Modifier.fillMaxSize(),
                    contentWindowInsets = WindowInsets.safeDrawing
                ) { innerPadding ->
                    AppNavigation(
                        navController = navHostController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

/*
* Testing...
*
* */
/**
 * Data class to hold screen configurations like title, back button visibility, and icons.
 */
data class ScreenConfig(
    val title: String,
    val showBackButton: Boolean = false,
    val actions: List<@Composable () -> Unit> = emptyList(),
)

/**
 * Function to get the screen config dynamically based on the route.
 */
@Composable
fun getScreenConfig(navController: NavController): ScreenConfig {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    println("Current Route: $currentRoute")
    return when (currentRoute) {
        "home" -> ScreenConfig(
            title = "Home",
            actions = listOf {
                IconButton(onClick = { /* Handle search click */ }) {
                    Icon(Icons.Filled.Search, contentDescription = "Search")
                }
            }
        )

        "stocks" -> ScreenConfig(
            title = "Stock Details",
            showBackButton = true,
            actions = listOf {
                IconButton(onClick = { /* Handle refresh */ }) {
                    Icon(Icons.Filled.Refresh, contentDescription = "Refresh")
                }
            }
        )

        "profile" -> ScreenConfig(
            title = "User Profile",
            showBackButton = true,
            actions = listOf {
                IconButton(onClick = { /* Handle settings */ }) {
                    Icon(Icons.Filled.Settings, contentDescription = "Settings")
                }
            }
        )

        else -> ScreenConfig(title = "Stocks App")
    }
}

/**
 * Dynamic TopAppBar that changes title, icons, and back button based on the current screen.
 */
@Composable
fun DynamicTopAppBar(navController: NavController) {
    val screenConfig = getScreenConfig(navController)

    TopAppBar(
        title = { Text(text = screenConfig.title) },
        navigationIcon = if (screenConfig.showBackButton) {
            {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        } else {
            null
        },
        actions = {
            screenConfig.actions.forEach { action ->
                action()
            }
        }
    )
}
