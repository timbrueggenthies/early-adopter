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
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.example.androiddevchallenge.data.Dog
import com.example.androiddevchallenge.data.createDogs
import com.example.androiddevchallenge.ui.screens.DogDetailScreen
import com.example.androiddevchallenge.ui.screens.DogListScreen
import com.example.androiddevchallenge.ui.theme.MyTheme
import java.util.Stack

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                MyApp()
            }
        }
    }
}

// Start building your app here!
@Composable
fun MyApp() {
    val context = LocalContext.current
    val appNavigation = remember { AppNavigation(AppScreen.DogList(createDogs(context, 20))) }
    Surface(color = MaterialTheme.colors.background) {
        when (val screen = appNavigation.screen.value) {
            is AppScreen.DogList -> DogListScreen(appNavigation, screen.dogs)
            is AppScreen.DogDetail -> DogDetailScreen(appNavigation, screen.dog)
        }
    }
}

sealed class AppScreen {
    class DogList(val dogs: List<Dog>) : AppScreen()
    class DogDetail(val dog: Dog) : AppScreen()
}

class AppNavigation(initialScreen: AppScreen) {

    private var history = Stack<AppScreen>()

    private var currentScreen = mutableStateOf(initialScreen)

    val screen: State<AppScreen>
        get() = currentScreen

    fun pushScreen(screen: AppScreen) {
        history.push(currentScreen.value)
        currentScreen.value = screen
    }

    fun pop() {
        history.pop()?.let { currentScreen.value = it }
    }
}
