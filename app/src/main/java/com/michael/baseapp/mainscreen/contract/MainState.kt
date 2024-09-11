package com.michael.baseapp.mainscreen.contract

import com.michael.base.contract.BaseState

data class MainState(
    override val isLoading: Boolean = false,
    override val errorState: Boolean = false,
) : BaseState {
    companion object {
        val initialState = MainState()
    }
}
