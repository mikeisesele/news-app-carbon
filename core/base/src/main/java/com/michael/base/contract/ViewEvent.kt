package com.michael.base.contract

import com.michael.base.model.MessageState

sealed class ViewEvent {
    data class Navigate(val target: NavigationTarget) : ViewEvent()
    data class DisplayMessage(val message: MessageState) : ViewEvent()
    data class Effect(val effect: SideEffect) : ViewEvent()
}
