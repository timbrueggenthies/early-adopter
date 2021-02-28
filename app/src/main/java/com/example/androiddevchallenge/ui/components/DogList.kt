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

import androidx.annotation.DrawableRes
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.data.Dog
import com.example.androiddevchallenge.ui.common.CompositeCornerSize
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun DogList(dogs: List<Dog>, onDogClick: (Dog) -> Unit, header: @Composable () -> Unit = { }) {
    var selectedDog by remember { mutableStateOf<Dog?>(null) }
    val color = MaterialTheme.colors.primaryVariant
    LazyColumn {
        item {
            header()
        }
        items(dogs) { dog ->
            val selected = selectedDog == dog
            DogListItem(
                dog = dog, color = color, expanded = selected,
                onClick = {
                    selectedDog = if (selectedDog == dog) null else dog
                },
                onDogClick = onDogClick
            )
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun DogListItem(
    dog: Dog,
    expanded: Boolean,
    color: Color,
    onClick: () -> Unit = { },
    onDogClick: (Dog) -> Unit = { }
) {
    val transition = updateTransition(targetState = expanded)

    val itemHeight by transition.animateDp {
        when (it) {
            true -> 160.dp
            false -> 88.dp
        }
    }

    ExpandableCard(
        expanded = expanded,
        modifier = Modifier
            .clickable { onClick() }
            .height(itemHeight)
    ) {
        val containerHorizontalPadding by animateDpAsState(targetValue = if (expanded) 0.dp else 16.dp)
        val containerVerticalPadding by animateDpAsState(targetValue = if (expanded) 0.dp else 8.dp)
        Row(
            Modifier
                .fillMaxWidth()
                .padding(containerHorizontalPadding, containerVerticalPadding)
        ) {
            DogImage(dog.image, transition, color)
            DogInfo(dog, transition, onDogClick)
        }
    }
}

@Composable
private fun DogInfo(
    dog: Dog,
    transition: Transition<Boolean>,
    onDogClick: (Dog) -> Unit
) {
    Column(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(start = 16.dp)
    ) {
        Text(text = dog.name, style = MaterialTheme.typography.h6)
        val formattedString = remember(dog.availableSince) {
            SimpleDateFormat("dd. MMMM yyyy", Locale.getDefault()).format(dog.availableSince)
        }
        Text(
            text = stringResource(id = R.string.dog_list_shelter_duration, formattedString),
            style = MaterialTheme.typography.caption
        )
        val visibility by transition.animateFloat {
            when (it) {
                true -> 1f
                false -> 0f
            }
        }
        TextButton(
            onClick = { onDogClick(dog) },
            modifier = Modifier
                .align(Alignment.End)
                .alpha(visibility)
                .padding(16.dp)
        ) {
            Text(text = stringResource(R.string.dog_list_button_learn_more))
        }
    }
}

@Composable
private fun DogImage(
    @DrawableRes dogImage: Int,
    transition: Transition<Boolean>,
    color: Color
) {
    val image = painterResource(id = dogImage)
    val transitionData = createDogImageTransition(transition = transition)
    val cornerSizeCompact = CornerSize(50)
    val cornerSizeExpanded = CornerSize(6.dp)
    val cornerSize = remember { CompositeCornerSize(cornerSizeExpanded, cornerSizeCompact, 0f) }
    cornerSize.progress = transitionData.cornerRadiusProgress
    Image(
        painter = image,
        contentDescription = stringResource(R.string.cd_dog_image),
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .aspectRatio(1f)
            .fillMaxHeight()
            .border(
                transitionData.borderWidth * 3,
                color = color.copy(alpha = 0.5f * transitionData.borderAlpha),
                RoundedCornerShape(cornerSize, cornerSize, cornerSize, cornerSize)
            )
            .border(
                transitionData.borderWidth,
                color = color.copy(alpha = transitionData.borderAlpha),
                RoundedCornerShape(cornerSize, cornerSize, cornerSize, cornerSize)
            )
            .padding(6.dp)
            .clip(RoundedCornerShape(cornerSize, cornerSize, cornerSize, cornerSize))
    )
}

@Composable
private fun createDogImageTransition(transition: Transition<Boolean>): DogImageTransitionData {
    val borderWidth = transition.animateDp {
        when (it) {
            true -> 0.dp
            false -> 2.dp
        }
    }
    val cornerRadiusProgress = transition.animateFloat {
        when (it) {
            true -> 0f
            false -> 1f
        }
    }
    val borderAlpha = transition.animateFloat {
        when (it) {
            true -> 0f
            false -> 1f
        }
    }
    return DogImageTransitionData(borderWidth, cornerRadiusProgress, borderAlpha)
}

private class DogImageTransitionData(
    borderWidth: State<Dp>,
    cornerRadiusProgress: State<Float>,
    borderAlpha: State<Float>
) {
    val borderWidth by borderWidth
    val cornerRadiusProgress by cornerRadiusProgress
    val borderAlpha by borderAlpha
}
