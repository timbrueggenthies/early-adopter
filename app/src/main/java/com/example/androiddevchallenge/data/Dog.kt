package com.example.androiddevchallenge.data

import androidx.annotation.DrawableRes
import java.util.*

class Dog(
    val name: String,
    val description: String,
    val breed: String,
    val availableSince: Date,
    val weight: Float,
    val height: Float,
    val age: Age,
    val properties: List<String>,
    @DrawableRes val image: Int
)

sealed class Age(val amount: Int) {
    class Years(amount: Int): Age(amount)
    class Months(amount: Int): Age(amount)
}
