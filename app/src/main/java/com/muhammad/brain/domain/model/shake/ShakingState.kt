package com.muhammad.brain.domain.model.shake

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer

@Immutable
class SnakingState(
    private val strength: ShakeStrength,
    private val direction: ShakeDirection,
) {
    val xPosition = Animatable(0f)
    suspend fun snake(duration: Int = 50) {
        val shakeAnimation: AnimationSpec<Float> = tween(durationMillis = duration)
        when (direction) {
            ShakeDirection.LEFT -> shakeToLeft(shakeAnimation)
            ShakeDirection.RIGHT -> shakeToRight(shakeAnimation)
            ShakeDirection.LEFT_AND_RIGHT -> shakeLeftThenRight(shakeAnimation)
            ShakeDirection.RIGHT_THEN_LEFT -> shakeRightThenLeft(shakeAnimation)
        }
    }

    private suspend fun shakeToLeft(animationSpec: AnimationSpec<Float>) {
        repeat(2) {
            xPosition.animateTo(-strength.value, animationSpec)
            xPosition.animateTo(0f, animationSpec = animationSpec)
        }
    }

    private suspend fun shakeToRight(animationSpec: AnimationSpec<Float>) {
        repeat(2) {
            xPosition.animateTo(strength.value, animationSpec)
            xPosition.animateTo(0f, animationSpec = animationSpec)
        }
    }

    private suspend fun shakeRightThenLeft(animationSpec: AnimationSpec<Float>) {
        repeat(2) {
            xPosition.animateTo(strength.value, animationSpec)
            xPosition.animateTo(0f, animationSpec)
            xPosition.animateTo(-strength.value / 2, animationSpec)
            xPosition.animateTo(0f, animationSpec)
        }
    }
    private suspend fun shakeLeftThenRight(animationSpec: AnimationSpec<Float>) {
        repeat(2) {
            xPosition.animateTo(-strength.value, animationSpec)
            xPosition.animateTo(0f, animationSpec)
            xPosition.animateTo(strength.value / 2, animationSpec)
            xPosition.animateTo(0f, animationSpec)
        }
    }
}

@Composable
fun rememberShakingState(
    strength : ShakeStrength = ShakeStrength.Normal,
    direction: ShakeDirection = ShakeDirection.LEFT_AND_RIGHT
) : SnakingState{
    return remember{
        SnakingState(strength, direction)
    }
}

fun Modifier.shakable(state: SnakingState) : Modifier{
    return graphicsLayer {
        translationX = state.xPosition.value
    }
}