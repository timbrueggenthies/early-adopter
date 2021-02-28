package com.example.androiddevchallenge.ui.common

import androidx.compose.foundation.shape.CornerSize
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.Density

class CompositeCornerSize(
    private val cornerSizeA: CornerSize,
    private val cornerSizeB: CornerSize,
    initialProgress: Float
) : CornerSize {

    var progress = initialProgress
        set(value) {
            checkProgressRange(value)
            field = value
        }

    override fun toPx(shapeSize: Size, density: Density): Float {
        val cornerPxA = cornerSizeA.toPx(shapeSize, density) * (1f - progress)
        val cornerPxB = cornerSizeB.toPx(shapeSize, density) * progress
        return cornerPxA + cornerPxB
    }

    private fun checkProgressRange(progress: Float) {
        require(progress in (0f..1f)) { "Progress needs to be in Range between 0f and 1f" }
    }

}
