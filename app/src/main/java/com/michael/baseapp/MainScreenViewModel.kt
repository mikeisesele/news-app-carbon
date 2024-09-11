package com.michael.baseapp

import com.michael.base.contract.BaseViewModel
import com.michael.base.contract.ViewEvent
import com.michael.base.providers.DispatcherProvider
import com.michael.baseapp.mainscreen.contract.MainSideEffect
import com.michael.baseapp.mainscreen.contract.MainState
import com.michael.baseapp.mainscreen.contract.MainViewAction
import com.michael.baseapp.navigation.ScreenSpec
import com.michael.baseapp.navigation.destinations
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    dispatcherProvider: DispatcherProvider,
) : BaseViewModel<MainState, MainViewAction>(
    MainState.initialState,
    dispatcherProvider,
) {

    init {
        updateState { state ->
            state.copy(
                screens = destinations,
            )
        }
    }
    override fun onViewAction(viewAction: MainViewAction) {
        when (viewAction) {
            is MainViewAction.DestinationClicked -> navigate(viewAction.screenSpec)
        }
    }

    private fun navigate(screenSpec: ScreenSpec) {
        dispatchViewEvent(
            ViewEvent.Effect(MainSideEffect.NavigateToDestination(screenSpec)),
        )
    }
}
