package com.muhammad.brain.presentation.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun AppLoading(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    lineLength: Dp = 10.dp,
    lineWidth: Dp = 3.dp,
    color: Color = MaterialTheme.colorScheme.primary,
    duration: Int = 800,
) {
    val infiniteTransition = rememberInfiniteTransition()
    val progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = duration,
                easing = LinearEasing
            )
        ), label = "progress"
    )
    Canvas(modifier = modifier.size(size)) {
        val sizePx = size.toPx()
        val lineLengthPx = lineLength.toPx()
        val center = Offset(x = sizePx / 2, y = sizePx / 2)
        val radius = (sizePx - lineLengthPx) / 2
        val lines = 10
        for (i in 0 until lines) {
            val angle = (i * 360f / lines) * (PI / 180)
            val currentActiveIndex = (progress * lines).toInt() % lines
            val distanceFromActive = minOf(
                abs(i - currentActiveIndex),
                abs(i - currentActiveIndex + lines),
                abs(i - currentActiveIndex - lines)
            )
            val alpha = when (distanceFromActive) {
                0 -> 1f
                1 -> 0.7f
                2 -> 0.4f
                3 -> 0.2f
                else -> 0.1f
            }
            val startX = center.x + cos(angle) * radius
            val startY = center.y + sin(angle) * radius
            val endX = center.x + cos(angle) * (radius + lineLengthPx)
            val endY = center.y + sin(angle) * (radius + lineLengthPx)
            drawLine(
                color = color,
                start = Offset(startX.toFloat(), startY.toFloat()),
                end = Offset(endX.toFloat(), endY.toFloat()),
                strokeWidth = lineWidth.toPx(), alpha = alpha, cap = StrokeCap.Round
            )
        }
    }
}