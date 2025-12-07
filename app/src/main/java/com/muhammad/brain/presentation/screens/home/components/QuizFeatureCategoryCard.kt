package com.muhammad.brain.presentation.screens.home.components

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.muhammad.brain.R
import com.muhammad.brain.domain.model.QuizCategory
import com.muhammad.brain.presentation.components.SecondaryButton
import com.muhammad.brain.utils.createCategoryLabel

@Composable
fun QuizFeatureCategoryCard(
    modifier: Modifier = Modifier,
    blurRadius: Dp = 32.dp,
    onClick: (QuizCategory) -> Unit,
    category: QuizCategory,
) {
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
            .clickable {
                onClick(category)
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
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = category.name,
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = Color.White,
                        textAlign = TextAlign.Left,
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = createCategoryLabel(category.name),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.White
                    )
                )
                Spacer(Modifier.height(8.dp))
                SecondaryButton(text =  stringResource(R.string.take_quiz), onClick = {
                    onClick(category)
                }, contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp))
            }
            Image(
                painter = painterResource(category.icon),
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )
        }
    }
}