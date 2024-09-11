package com.michael.baseapp.mainscreen.contract

import com.michael.base.contract.SideEffect
import com.michael.baseapp.navigation.ScreenSpec

sealed class MainSideEffect : SideEffect {
    data class NavigateToDestination(val screenSpec: ScreenSpec) : MainSideEffect()
}
