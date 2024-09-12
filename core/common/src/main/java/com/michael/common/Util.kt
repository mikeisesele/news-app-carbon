package com.michael.common

import android.content.Context
import android.util.Log
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
