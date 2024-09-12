package com.michael.base.model

sealed class MessageState {
    data class SimpleDialog(val message: String) : MessageState()
    data class Snack(val message: String, val action: (() -> Unit)? = null) : MessageState()
    data class Inline(val message: String, val action: (() -> Unit)? = null) : MessageState()

    data class Toast(val message: String) : MessageState()
}
