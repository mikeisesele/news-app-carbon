package com.michael.base.model

/**
 * Modelling different message states, this class is used in the ViewState giving scalable solution
 * to define multiple types of messages on the view.
 */
sealed class MessageState {
    data class SimpleDialog(val message: String) : MessageState()
    data class Snack(val message: String, val action: (() -> Unit)? = null) : MessageState()
    data class Inline(val message: String, val action: (() -> Unit)? = null) : MessageState()
    data class Toast(val message: String) : MessageState()
}
