package com.sujith.kotlin.stocksapp.presentation.company_info.ui

import android.graphics.Paint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sujith.kotlin.stocksapp.domine.models.IntraDayInfoModel
import com.sujith.kotlin.stocksapp.presentation.company_info.viewmodel.CompanyInfoViewmodel
import kotlin.math.roundToInt

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StockChart(
    infos: List<IntraDayInfoModel> = emptyList(),
    modifier: Modifier = Modifier,
    graphColor: Color = Color.Cyan, // Improved contrast
) {
    val spacing = 100f
    val transparentGraphColor = remember { graphColor.copy(alpha = 0.3f) }
    val upperValue =
        remember(infos) { (infos.maxOfOrNull { it.close }?.plus(1)?.roundToInt() ?: 1) }
    val lowerValue = remember(infos) { (infos.minOfOrNull { it.close }?.toInt() ?: 0) }

    val density = LocalDensity.current
    val textPaint = remember(density) {
        Paint().apply {
            color = android.graphics.Color.WHITE
            textAlign = Paint.Align.CENTER
            textSize = density.run { 12.sp.toPx() }
        }
    }

    Canvas(modifier = modifier) {
        val spacePerHour = (size.width - spacing) / infos.size
        (0 until infos.size step 4).forEach { i ->
            val info = infos[i]
            val hour = info.date.hour
            drawContext.canvas.nativeCanvas.apply {
                drawText(hour.toString(), spacing + i * spacePerHour, size.height - 20, textPaint)
            }
        }

        val priceInterval = (upperValue - lowerValue) / 5f
        (0..5).forEach { i ->
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    ((lowerValue + priceInterval * i).roundToInt()).toString(),
                    50f,
                    size.height - spacing - i * (size.height - spacing) / 5f,
                    textPaint
                )
            }
        }

        val strokePath = Path().apply {
            val height = size.height - spacing
            for (i in infos.indices) {
                val info = infos[i]
                val x = spacing + i * spacePerHour
                val y = height - ((info.close - lowerValue) / (upperValue - lowerValue) * height)
                if (i == 0) moveTo(x, y.toFloat()) else lineTo(x, y.toFloat())
            }
        }

        val fillPath = android.graphics.Path(strokePath.asAndroidPath()).asComposePath().apply {
            lineTo(size.width - spacing, size.height - spacing)
            lineTo(spacing, size.height - spacing)
            close()
        }

        drawPath(fillPath, Brush.verticalGradient(listOf(transparentGraphColor, Color.Transparent)))
        drawPath(strokePath, graphColor, style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round))
    }
}
