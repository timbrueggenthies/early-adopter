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

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.AppNavigation
import com.example.androiddevchallenge.AppScreen
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.data.Dog
import com.example.androiddevchallenge.ui.components.DogList

@Composable
fun DogListScreen(appNavigation: AppNavigation, dogs: List<Dog> = emptyList()) {
    DogList(
        dogs = dogs,
        header = {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = stringResource(R.string.dog_list_title),
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colors.primary
                )
                Text(
                    text = stringResource(id = R.string.dog_default_description, dogs.size),
                    style = MaterialTheme.typography.h6
                )
            }
        },
        onDogClick = { dog -> appNavigation.pushScreen(AppScreen.DogDetail(dog)) }
    )
}
