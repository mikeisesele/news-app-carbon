package com.michael.common

import android.content.Context
import android.widget.Toast
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun displayToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

// Extension function to convert date string to readable format
fun String.toReadableDate(): String {
    // Define the input format (yyyy-MM-dd HH:mm:ss)
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    // Parse the string to a LocalDateTime object
    val dateTime = LocalDateTime.parse(this, inputFormatter)

    // Define the output format (d MMMM yyyy)
    val outputFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.getDefault())

    // Return the formatted date string
    return dateTime.format(outputFormatter)
}


fun Int.randomFrom(): Int {
    return (this + 100 .. 1000000000).random()
}

// Extension function to clean the message
fun String.cleanMessage(): String {
    val parts = this.split(" ")
    // Ensure the message starts with HTTP and contains a 3-digit code before the message
    val errorCode = parts.getOrNull(1) ?: "XXX"  // Default to "XXX" if the second part is missing
    return if (parts.firstOrNull() == "HTTP" && errorCode.matches(Regex("\\d{3}"))) {
        parts.drop(2).joinToString(" ")
    } else {
        this  // Return the original string if it doesn't meet the condition
    }
}
