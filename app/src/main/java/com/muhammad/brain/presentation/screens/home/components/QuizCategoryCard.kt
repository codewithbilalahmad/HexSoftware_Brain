package com.muhammad.brain.presentation.screens.home.components

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.muhammad.brain.R
import com.muhammad.brain.domain.model.QuizCategory

@Composable
fun QuizCategoryCard(
    modifier: Modifier = Modifier,
    category: QuizCategory,
    blurRadius: Dp = 32.dp,
    onClick: () -> Unit,
) {
    val density = LocalDensity.current
    val radiusPx = with(density) {
        blurRadius.toPx()
    }
    val blurModifier = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        Modifier.graphicsLayer {
            renderEffect = RenderEffect.createBlurEffect(radiusPx, radiusPx, Shader.TileMode.CLAMP).asComposeRenderEffect()
        }
    } else {
        Modifier.blur(blurRadius)
    }
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .clickable {
                onClick()
            }) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(brush = Brush.horizontalGradient(category.brushColors))
                .then(blurModifier)
        )
        Row(
            modifier = modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(35.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .border(
                            width = 2.dp,
                            color = Color.White,
                            shape = RoundedCornerShape(8.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_quiz),
                        contentDescription = null, tint = Color.White,
                        modifier = Modifier.size(22.dp)
                    )
                }
                Spacer(Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.level_1),
                    style = MaterialTheme.typography.bodyLarge.copy(color = Color.White)
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = category.name,
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = Color.White,
                        textAlign = TextAlign.Left,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
            Image(
                painter = painterResource(category.icon),
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )
        }
    }
}