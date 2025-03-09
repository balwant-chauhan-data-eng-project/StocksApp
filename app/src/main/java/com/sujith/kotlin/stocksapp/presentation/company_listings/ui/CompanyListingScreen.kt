package com.sujith.kotlin.stocksapp.presentation.company_listings.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.Modifier.Companion
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sujith.kotlin.stocksapp.presentation.company_listings.viewmodel.CompanyListingsEvent
import com.sujith.kotlin.stocksapp.presentation.company_listings.viewmodel.CompanyListingsViewmodel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CompanyListingScreen(
    modifier: Modifier = Modifier,
    navigation: (symbol: String) -> Unit,
    viewModel: CompanyListingsViewmodel = hiltViewModel<CompanyListingsViewmodel>(),
) {

    val state = viewModel.state

    val onRefreshState = rememberPullRefreshState(
        refreshing = state.isRefreshing,
        onRefresh = {
            viewModel.onEven(CompanyListingsEvent.Refresh)
        }
    )
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = state.searchQuery,
            onValueChange = {
                viewModel.onEven(
                    CompanyListingsEvent.OnSearchQueryChange(it)
                )
            },
            modifier = Modifier
                .padding(9.dp)
                .fillMaxWidth(),
            placeholder = {
                Text(text = "Search...")
            },
            maxLines = 1,
            leadingIcon = {
                Icon(imageVector = Icons.Outlined.Search, contentDescription = "Search")
            }
        )
        PullRefreshIndicator(
            state.isRefreshing,
            onRefreshState,
            Modifier.align(Alignment.CenterHorizontally)
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(state.companies.size) { i ->
                val company = state.companies[i]
                CompanyChildItem(
                    company = company,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            // Todo : navigate to detail screen
                            navigation(company.symbol)
                        }
                        .padding(16.dp)
                )

                if (i < state.companies.size) {
                    Divider(
                        Modifier.padding(
                            horizontal = 16.dp
                        )
                    )
                }

            }
        }
    }
}