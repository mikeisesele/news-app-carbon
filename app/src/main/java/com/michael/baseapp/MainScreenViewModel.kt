package com.michael.baseapp

import com.michael.base.contract.BaseViewModel
import com.michael.base.contract.ViewEvent
import com.michael.base.providers.DispatcherProvider
import com.michael.baseapp.mainscreen.contract.MainSideEffect
import com.michael.baseapp.mainscreen.contract.MainState
import com.michael.baseapp.mainscreen.contract.MainViewAction
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    dispatcherProvider: DispatcherProvider,
) : BaseViewModel<MainState, MainViewAction>(
    MainState.initialState,
    dispatcherProvider,
) {


    override fun onViewAction(viewAction: MainViewAction) {
//        when (viewAction) {
//           // TODO
//        }
    }

}
