package com.muhammad.brain.domain.model.shake

sealed class ShakeStrength(val value : Float){
    data object Normal : ShakeStrength(17f)
    data object Strong : ShakeStrength(40f)
    data class Custom(val strength : Float) : ShakeStrength(strength)
}