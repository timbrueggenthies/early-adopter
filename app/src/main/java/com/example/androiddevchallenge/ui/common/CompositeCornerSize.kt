/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
