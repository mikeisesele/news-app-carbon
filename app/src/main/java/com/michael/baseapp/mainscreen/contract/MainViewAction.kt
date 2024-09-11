package com.michael.baseapp.mainscreen.contract

import com.michael.baseapp.navigation.ScreenSpec

sealed interface MainViewAction {

    data class DestinationClicked(val screenSpec: ScreenSpec) : MainViewAction
}
