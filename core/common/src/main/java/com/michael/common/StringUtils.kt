package com.michael.common

import java.util.Locale

/**
 * Simple utility that converts a string into title case.
 * This is mainly due to [String.capitalize] being deprecated.
 * */
fun String.titleCase(): String {
    val src = this
    return buildString {
        if (src.isNotEmpty()) {
            append(src[0].uppercase(Locale.getDefault()))
        }
        if (src.length > 1) {
            append(src.substring(1).lowercase(Locale.getDefault()))
        }
    }
}
