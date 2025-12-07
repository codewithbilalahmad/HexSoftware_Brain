package com.muhammad.brain.presentation.screens.quiz.components

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.muhammad.brain.domain.model.QuizQuestion

@Composable
fun QuestionContent(modifier: Modifier = Modifier, question: QuizQuestion, blurRadius: Dp = 32.dp) {
    val density = LocalDensity.current
    val radiusPx = with(density) {
        blurRadius.toPx()
    }
    val blurModifier = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        Modifier.graphicsLayer {
            renderEffect = RenderEffect.createBlurEffect(radiusPx, radiusPx, Shader.TileMode.CLAMP)
                .asComposeRenderEffect()
        }
    } else {
        Modifier.blur(blurRadius)
    }
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(brush = Brush.horizontalGradient(question.brushColors))
                .then(blurModifier)
        )
        Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp), contentAlignment = Alignment.Center) {
            Text(
                text = question.question,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            )
        }
    }
}