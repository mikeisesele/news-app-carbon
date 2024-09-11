package com.michael.baseapp.mainscreen.contract

import com.michael.base.contract.BaseState
import com.michael.base.model.MessageState
import com.michael.baseapp.navigation.ScreenSpec
import com.michael.baseapp.navigation.destinations
import com.michael.common.ImmutableList

data class MainState(
    override val isLoading: Boolean = false,
    override val errorState: MessageState? = null,
    val screens: ImmutableList<ScreenSpec> = destinations,
) : BaseState {
    companion object {
        val initialState = MainState()
    }
}
