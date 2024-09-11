package com.michael.common

import android.content.Context
import android.util.Log
import android.widget.Toast

fun displayToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun <T> Any?.log(line: Any? = null, c: Class<T>) {
    Log.d("BASE_APP: ${c::class.java.name} line - ${line?.toString()}", this.toString())
}
