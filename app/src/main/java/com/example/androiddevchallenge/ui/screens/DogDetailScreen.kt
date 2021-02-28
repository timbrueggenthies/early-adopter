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
package com.example.androiddevchallenge.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LinearScale
import androidx.compose.material.icons.filled.MonitorWeight
import androidx.compose.material.icons.filled.Pets
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.AppNavigation
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.data.Age
import com.example.androiddevchallenge.data.Dog

@Composable
fun DogDetailScreen(appNavigation: AppNavigation, dog: Dog) {
    Box {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            DogDetailAppBar(appNavigation)
            DogDetailContent(dog)
        }
        FloatingActionButton(
            onClick = { },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
        ) {
            Text(text = stringResource(R.string.button_adopt_me), modifier = Modifier.padding(16.dp))
        }
    }
}

@Composable
private fun DogDetailContent(dog: Dog) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 16.dp, 16.dp, 64.dp)
    ) {
        DogDetailImage(dog)
        DogDetailBaseInfo(dog)
        DogDetailCard(dog)
    }
}

@Composable
private fun ColumnScope.DogDetailBaseInfo(dog: Dog) {
    Column(
        modifier = Modifier.Companion
            .align(Alignment.CenterHorizontally)
            .offset(0.dp, (-16).dp)
            .clip(
                RoundedCornerShape(
                    CornerSize(8.dp),
                    CornerSize(8.dp),
                    CornerSize(8.dp),
                    CornerSize(8.dp)
                )
            )
            .background(MaterialTheme.colors.primary)
            .padding(16.dp, 8.dp)
    ) {
        Text(
            text = dog.name,
            style = MaterialTheme.typography.h4,
            color = MaterialTheme.colors.onPrimary,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        val resId = if (dog.age is Age.Years) R.string.age_years else R.string.age_months
        val ageString = stringResource(resId, dog.age.amount)
        Text(
            text = ageString,
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onPrimary,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }

    Column(modifier = Modifier.padding(16.dp)) {
        DogInfo(icon = Icons.Filled.Pets, info = dog.breed)
        Spacer(modifier = Modifier.height(16.dp))
        DogInfo(icon = Icons.Filled.MonitorWeight, info = stringResource(id = R.string.unit_kilogram, dog.weight))
        Spacer(modifier = Modifier.height(16.dp))
        DogInfo(icon = Icons.Filled.LinearScale, info = stringResource(id = R.string.unit_centimeter, dog.height))
    }
}

@Composable
private fun DogDetailCard(dog: Dog) {
    Card(
        elevation = 0.dp,
        border = BorderStroke(1.dp, MaterialTheme.colors.onSurface.copy(alpha = 0.2f)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = "\"${dog.description}\"",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.body1
            )
            Text(
                text = stringResource(R.string.placeholder_caretaker),
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.End),
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.caption,
                fontStyle = FontStyle.Italic
            )
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .horizontalScroll(rememberScrollState())
            ) {
                dog.properties.forEach {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.body1,
                        fontStyle = FontStyle.Italic,
                        color = MaterialTheme.colors.onSecondary,
                        modifier = Modifier
                            .clip(
                                RoundedCornerShape(
                                    CornerSize(4.dp),
                                    CornerSize(4.dp),
                                    CornerSize(4.dp),
                                    CornerSize(4.dp)
                                )
                            )
                            .background(MaterialTheme.colors.secondaryVariant)
                            .padding(8.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                }
            }
        }
    }
}

@Composable
private fun ColumnScope.DogDetailImage(dog: Dog) {
    val image = painterResource(id = dog.image)
    Image(
        image,
        stringResource(R.string.cd_dog_image),
        modifier = Modifier
            .heightIn(max = 250.dp)
            .aspectRatio(1f)
            .clip(
                RoundedCornerShape(
                    CornerSize(8.dp),
                    CornerSize(8.dp),
                    CornerSize(8.dp),
                    CornerSize(8.dp)
                )
            )
            .align(Alignment.CenterHorizontally),
        contentScale = ContentScale.Crop
    )
}

@Composable
private fun DogDetailAppBar(appNavigation: AppNavigation) {
    TopAppBar(backgroundColor = MaterialTheme.colors.background, elevation = 0.dp) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp)
                .align(Alignment.CenterVertically)
        ) {
            Icon(
                Icons.Filled.ArrowBack,
                contentDescription = stringResource(R.string.cd_arrow_back),
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .clickable { appNavigation.pop() }
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = stringResource(R.string.dog_detail_appbar_title),
                style = MaterialTheme.typography.h5,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}

@Composable
fun DogInfo(icon: ImageVector, info: String) {
    Row(
        modifier = Modifier
            .border(2.dp, MaterialTheme.colors.primary, CircleShape)
            .clip(CircleShape)
            .background(MaterialTheme.colors.primary.copy(0.1f))
            .padding(PaddingValues(end = 32.dp))
    ) {
        Icon(
            icon,
            contentDescription = null,
            tint = MaterialTheme.colors.onPrimary,
            modifier = Modifier
                .clip(CircleShape)
                .background(MaterialTheme.colors.primary)
                .padding(8.dp)
                .align(Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = info,
            style = MaterialTheme.typography.h6,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}
