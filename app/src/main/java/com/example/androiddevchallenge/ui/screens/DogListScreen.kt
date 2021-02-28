package com.example.androiddevchallenge.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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

