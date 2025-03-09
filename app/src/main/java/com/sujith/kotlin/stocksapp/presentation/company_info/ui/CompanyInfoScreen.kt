package com.sujith.kotlin.stocksapp.presentation.company_info.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sujith.kotlin.stocksapp.domine.models.CompanyInfoModel
import com.sujith.kotlin.stocksapp.presentation.company_info.viewmodel.CompanyInfoViewmodel
import com.sujith.kotlin.stocksapp.ui.theme.DarkBlue


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CompanyInfoScreen(
    modifier: Modifier = Modifier,
    viewModel: CompanyInfoViewmodel = hiltViewModel(),
    symbol: String,
) {
    val state = viewModel.state
    if (state.error == null) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(DarkBlue)
                .padding(16.dp)
        ) {
            state.company?.let {
                Text(
                    text = it.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(9.dp))
                Text(
                    text = it.symbol,
                    fontStyle = FontStyle.Italic,
                    fontSize = 18.sp,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                Divider(modifier = Modifier.fillMaxWidth())
                Spacer(Modifier.height(8.dp))
                Text(text = it.industry, fontSize = 18.sp, modifier = Modifier.fillMaxWidth())
                Text(text = it.country, fontSize = 18.sp, modifier = Modifier.fillMaxWidth())
                Spacer(Modifier.height(8.dp))
                Divider(modifier = Modifier.fillMaxWidth())
                Spacer(Modifier.height(8.dp))
                Text(text = it.description, fontSize = 18.sp, modifier = Modifier.fillMaxWidth())
                if (state.stockInfo.isNotEmpty()) {
                    Spacer(Modifier.height(16.dp))
                    Text(text = "Market Summary")
                    Spacer(Modifier.height(16.dp))
                    StockChart(
                        infos = state.stockInfo,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                    )
                }
            }
        }
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (state.isLoading) {
            CircularProgressIndicator()
        } else if (state.error != null) {
            Box(modifier = Modifier.padding(16.dp)) {
                Text(text = state.error, color = MaterialTheme.colorScheme.error, fontSize = 16.sp)
            }
        }
    }
}
