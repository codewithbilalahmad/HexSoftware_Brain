package com.muhammad.brain.utils

import android.annotation.SuppressLint
import androidx.compose.ui.graphics.Color
import com.muhammad.brain.R
import com.muhammad.brain.domain.model.LeaderboardUser

fun createBeautifulGradient(): List<Color> {
    val colorPool = listOf(
        Color(0xFF81C784), // Soft Green
        Color(0xFF64B5F6), // Soft Blue
        Color(0xFF4DD0E1), // Soft Cyan
        Color(0xFFFF8A65), // Soft Orange
        Color(0xFFBA68C8), // Soft Purple
        Color(0xFF4DB6AC), // Soft Teal
        Color(0xFF7986CB), // Soft Indigo
        Color(0xFFF06292), // Soft Pink
        Color(0xFFE57373), // Soft Red
    )

    return colorPool.shuffled().take(3)
}

fun createCategoryLabel(name: String): String {
    return when (name) {
        "General Knowledge" -> "Test Your Knowledge"
        "Books" -> "Explore Famous Books"
        "Film" -> "Cinema Trivia Fun"
        "Music" -> "Guess the Tunes"
        "Musicals & Theatres" -> "Stage & Musical Trivia"
        "Television" -> "TV Shows Challenge"
        "Video Games" -> "Gaming Trivia Battle"
        "Board Games" -> "Classic Board Challenges"

        "Science & Nature" -> "Discover Science Facts"
        "Computers" -> "Tech & Computer Quiz"
        "Mathematics" -> "Numbers and Logic"
        "Gadgets" -> "Modern Tech Gadgets"

        "Mythology" -> "Myths and Legends"
        "Sports" -> "Sports Knowledge Test"
        "Geography" -> "World Map Trivia"
        "History" -> "Explore Past Events"
        "Politics" -> "World Politics Quiz"

        "Art" -> "Creative Art Trivia"
        "Celebrities" -> "Famous People Quiz"
        "Animals" -> "Animal World Facts"
        "Vehicles" -> "Cars & Machines"

        "Comics" -> "Heroes and Comics"
        "Japanese Anime & Manga" -> "Anime & Manga Quiz"
        "Cartoon & Animations" -> "Cartoon Fun Trivia"

        else -> "Quiz Category"
    }
}

@SuppressLint("DefaultLocale")
fun formatTime(seconds: Int): String {
    val minutes = seconds / 60
    val seconds = seconds % 60
    return String.format("%02d:%02d", minutes, seconds)
}

fun getRandomMotivationLine(): String {
    val lines = listOf(
        "Keep practicing! Every quiz is a step forward.",
        "You're getting smarter with every question!",
        "Stay focused! Your brain is leveling up.",
        "Well done! Keep the momentum going.",
        "Great effort! Knowledge grows with consistency.",
        "Believe in yourself—you're doing amazing!",
        "Each quiz makes you better than before.",
        "Don't stop now—your brain loves challenges!",
        "Success is built one question at a time.",
        "Keep going! You're on the right path."
    )

    return lines.random()
}

fun generateRandomLeaderboardUsers(): List<LeaderboardUser> {
    val sampleNames = listOf(
        "Bilal", "Alex", "Hassan", "Umer", "John", "David", "Ali", "Owais",
        "Hamza", "Khan", "Rayyan", "Usman", "Amir", "Ibrahim", "Yusuf", "Kamran",
        "Arham", "Omar", "Michael", "Saad", "Farhan", "Zayan", "Hassan Raza",
        "Daniel", "Jibran", "Salman", "Kabir", "Raza", "Taha", "Zubair", "Adeel",
        "Faizan", "Shahzaib", "Rehan", "Ammar", "Noman", "Shayan", "Hanzala",
        "Furqan", "Haseeb", "Adnan", "Faisal", "Junaid", "Sarim", "Talha",
        "Arslan", "Imran", "Waqar", "Shahid", "Naveed"
    )
    val sampleImages = listOf(
        R.drawable.person1,
        R.drawable.person2,
        R.drawable.person3,
        R.drawable.person4,
        R.drawable.person5,
        R.drawable.person7,
        R.drawable.person8,
        R.drawable.person9,
    )
    val randomUsers = List(50) {index ->
        LeaderboardUser(
            rank = 0,
            username = sampleNames[index],
            image = sampleImages.random(),
            coin = (1000..8000).random()
        )
    }
    return randomUsers.sortedByDescending { it.coin }.mapIndexed { index, user ->
        user.copy(rank = index + 1)
    }
}



