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
package com.example.androiddevchallenge.ui.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ExpandableCard(
    expanded: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val transition = updateTransition(targetState = expanded)
    val transitionData = createExpandableCardTransition(transition)
    Surface(
        modifier = Modifier
            .padding(PaddingValues(transitionData.padding))
            .then(modifier),
        elevation = transitionData.elevation,
        color = transitionData.surfaceColor,
        shape = RoundedCornerShape(
            CornerSize(transitionData.cornerRadius),
            CornerSize(transitionData.cornerRadius),
            CornerSize(transitionData.cornerRadius),
            CornerSize(transitionData.cornerRadius)
        ),
        content = content
    )
}

@Composable
fun createExpandableCardTransition(transition: Transition<Boolean>): ExpandableCardTransitionData {
    val padding = transition.animateDp {
        when (it) {
            true -> 16.dp
            false -> 0.dp
        }
    }
    val cornerRadius = transition.animateDp {
        when (it) {
            true -> 8.dp
            false -> 0.dp
        }
    }
    val elevation = transition.animateDp {
        when (it) {
            true -> 4.dp
            false -> 0.dp
        }
    }
    val surfaceColor = transition.animateColor {
        when (it) {
            true -> MaterialTheme.colors.surface
            false -> Color.Transparent
        }
    }
    return ExpandableCardTransitionData(padding, cornerRadius, elevation, surfaceColor)
}

class ExpandableCardTransitionData(
    padding: State<Dp>,
    cornerRadius: State<Dp>,
    elevation: State<Dp>,
    surfaceColor: State<Color>
) {
    val padding by padding
    val cornerRadius by cornerRadius
    val elevation by elevation
    val surfaceColor by surfaceColor
}
