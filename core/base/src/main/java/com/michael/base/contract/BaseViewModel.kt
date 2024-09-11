package com.michael.base.contract

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.michael.base.providers.DispatcherProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * This is the common part of all ViewModels, dealing with ViewState and SideEffect propagation.
 */
abstract class BaseViewModel<ViewState : BaseState, Action>(
    initialState: ViewState,
    protected val dispatcherProvider: DispatcherProvider,
) : ViewModel() {

    private val _state: MutableStateFlow<ViewState> = MutableStateFlow(initialState)
    val state: StateFlow<ViewState> = _state.asStateFlow()

    private val eventsFlow = Channel<ViewEvent>(
        capacity = Int.MAX_VALUE,
        onBufferOverflow = BufferOverflow.SUSPEND,
        onUndeliveredElement = {
            Log.e(this.toString(), null, IllegalStateException("Missed view event $it"))
        },
    )
    val events
        get() = eventsFlow.receiveAsFlow()

    protected val currentState: ViewState
        get() = state.value

    abstract fun onViewAction(viewAction: Action)

    protected fun dispatchViewEvent(event: ViewEvent) {
        launch {
            eventsFlow.send(event)
        }
    }

    protected fun launch(
        block: suspend CoroutineScope.() -> Unit,
    ) {
        viewModelScope.launch(context = dispatcherProvider.main, block = block)
    }

    /**
     * Updates the [currentState] with the value returned from the [transform] lambda
     */
    protected fun updateState(transform: (ViewState) -> ViewState) {
        _state.update(transform)
    }
}
