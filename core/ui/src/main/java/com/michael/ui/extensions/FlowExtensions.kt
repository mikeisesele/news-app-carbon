package com.michael.ui.extensions

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

@Suppress("TooGenericExceptionCaught")
@Composable
fun <T> rememberStateWithLifecycle(
    stateFlow: StateFlow<T>,
    lifecycle: Lifecycle = LocalLifecycleOwner.current.lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
): State<T> {
    val initialValue = remember(stateFlow) {
        stateFlow.value
    }
    return produceState(
        key1 = stateFlow,
        key2 = lifecycle,
        key3 = minActiveState,
        initialValue = initialValue,
    ) {
        lifecycle.repeatOnLifecycle(minActiveState) {
            stateFlow.collect {
                this@produceState.value = it
            }
        }
    }
}

/**
 * Create an effect that matches the lifecycle of the call site.
 * If LandingScreen recomposes, the delay shouldn't start again.
 *
 * https://stackoverflow.com/a/71626121/6510726
 */
@SuppressLint("ComposableNaming")
@Composable
fun <T> Flow<T>.collectAsEffect(
    context: CoroutineContext = EmptyCoroutineContext,
    block: suspend (T) -> Unit,
) {
    LaunchedEffect(key1 = Unit) {
        onEach(block).flowOn(context).launchIn(this)
    }
}

suspend inline fun <T : Any?> Flow<T>.collectBy(
    onStart: () -> Unit = {},
    crossinline onEach: (T) -> Unit = { _ -> },
    crossinline onError: (Throwable) -> Unit = { _ -> },
) {
    try {
        onStart()
        catch { error -> onError(error) }
            .collect { item -> onEach(item) }
    } catch (e: Exception) {
        onError(e)
    }
}

suspend inline fun <T : Any?> Flow<T>.singleFlow(
    onStart: () -> Unit = {},
    crossinline onItemReceived: (T) -> Unit = { _ -> },
    crossinline onError: (Throwable) -> Unit = { _ -> },
) {
    try {
        onStart()
        flowOf(first())
            .catch { error -> onError(error) }
            .collect { item -> onItemReceived(item) }
    } catch (e: Exception) {
        onError(e)
    }
}
