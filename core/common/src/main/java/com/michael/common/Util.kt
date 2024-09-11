package com.michael.common

import android.content.Context
import android.util.Log
import android.widget.Toast

fun displayToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}


