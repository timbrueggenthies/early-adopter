package com.example.androiddevchallenge.data

import android.content.Context
import com.example.androiddevchallenge.R
import java.util.*
import kotlin.random.Random

fun createDogs(context: Context, amount: Int): List<Dog> {
    return (1..amount).map {
        createDog(context)
    }
}

fun createDog(context: Context): Dog {
    val name = dogNames.random()
    val description = context.getString(R.string.dog_default_description)
    val breed = dogBreeds.random()
    val weight = (60..400).random() / 10f
    val age = if (Random(0).nextBoolean()) {
        Age.Months((1..11).random())
    } else {
        Age.Years((1..16).random())
    }
    val height = (180..700).random() / 10f
    val image = dogImages.random()
    val daysInShelter = (1..1000).random()
    val availableSince =
        Calendar.getInstance().apply { add(Calendar.DAY_OF_MONTH, -daysInShelter) }.time
    val properties = properties.shuffled().take(3)
    return Dog(name, description, breed, availableSince, weight, height, age, properties, image)
}

val dogImages = listOf(
    R.drawable.dog_1,
    R.drawable.dog_2,
    R.drawable.dog_3,
    R.drawable.dog_4,
    R.drawable.dog_5,
    R.drawable.dog_6,
)

val properties = listOf(
    "Calm",
    "Wild",
    "Playful",
    "Loving",
    "Energizing",
    "Humble",
    "Noisy",
    "Curious"
)