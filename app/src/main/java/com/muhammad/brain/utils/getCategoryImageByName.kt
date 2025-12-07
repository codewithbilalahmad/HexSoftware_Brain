package com.muhammad.brain.utils

import com.muhammad.brain.R

fun getCategoryImageByName(name: String): Int {
    return when (name) {
        "General Knowledge" -> R.drawable.general_knowledge
        "Books" -> R.drawable.books
        "Film" -> R.drawable.film
        "Music" -> R.drawable.music
        "Musicals & Theatres" -> R.drawable.theater
        "Television" -> R.drawable.television
        "Video Games" -> R.drawable.games
        "Board Games" -> R.drawable.board_game
        "Science & Nature" -> R.drawable.science
        "Computers" -> R.drawable.computer
        "Mathematics" -> R.drawable.mathematic
        "Mythology" -> R.drawable.mythology
        "Sports" -> R.drawable.sports
        "Geography" -> R.drawable.geography
        "History" -> R.drawable.history
        "Politics" -> R.drawable.politics
        "Art" -> R.drawable.arts
        "Celebrities" -> R.drawable.celebrities
        "Animals" -> R.drawable.animals
        "Vehicles" -> R.drawable.vehicles
        "Comics" -> R.drawable.comics
        "Gadgets" -> R.drawable.gadgets
        "Japanese Anime & Manga" -> R.drawable.anime
        "Cartoon & Animations" -> R.drawable.cartoon
        else -> R.drawable.celebrities
    }
}
