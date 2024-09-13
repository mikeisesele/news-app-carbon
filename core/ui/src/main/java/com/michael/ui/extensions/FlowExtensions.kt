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
import com.michael.base.contract.SideEffect
import com.michael.base.contract.ViewEvent
import com.michael.base.providers.DispatcherProvider
import com.michael.easylog.logInline
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
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


suspend inline fun <reified T : SideEffect> (() -> Flow<SideEffect>).collectSideEffect(
    crossinline onEffect: (T) -> Unit
) {
    // Invoke the function to get the Flow<SideEffect>
    val flow = this()
    // Collects only the effects of type T and applies the callback
    flow
        .filterIsInstance<T>()
        .collect { effect ->
            onEffect(effect)
        }
}

suspend inline fun <reified T : SideEffect> Flow<ViewEvent>.collectAllEffect(
    crossinline onEffect: (T) -> Unit
) {
    this
        .filterIsInstance<ViewEvent.Effect>()
        .map { it.effect }
        .filterIsInstance<T>()
        .collect { effect ->
            onEffect(effect)
        }
}

fun <T> List<T>.asFlow(): Flow<List<T>> =
    flow { emit(this@asFlow) }
        .distinctUntilChanged()

fun <T> T.asFlow(): Flow<T> = flowOf(this)
    .distinctUntilChanged()

fun <T> T.asEmittedFlow(dispatcherProvider: DispatcherProvider): Flow<T> = flow {
    emit(this@asEmittedFlow) // Use this@asEmittedFlow to reference the receiver (the object T)
}.distinctUntilChanged()
    .flowOn(dispatcherProvider.io)


/**
 * Safely executes an operation by wrapping it in a try-catch block. Operations to be invoked
 * should not have a return type.
 *
 * @param operation The operation to be executed, taking no parameters and having no return type.
 * @param exceptionMessage The log message associated with the exception.
 * @param actionOnException An optional action to be executed in case of an exception.
 *                          It takes no parameters and is typically used for cleanup or logging.
 */
fun safeOperation(
    operation: () -> Unit,
    actionOnException: ((e:Exception) -> Unit)? = null,
    exceptionMessage: String,
) {
    try {
        operation.invoke()
    } catch (e: Exception) {
        //print stack trace
        e.printStackTrace()

        // Log the exception and associated log message
        exceptionMessage.logInline("safeOperation Exception: ")

        // Invoke the optional actionOnException (cleanup or additional logging)
        actionOnException?.invoke(e)
    }
}


/**
 * Safely executes a suspend operation by wrapping it in a try-catch block.
 *
 * @param operation The suspend operation to be executed.
 * @param actionOnException An optional action to be executed in case of an exception.
 *                          It takes a nullable string parameter representing the error message.
 * @param exceptionMessage The log message associated with the exception.
 */
suspend fun <T> safeSuspendOperation(
    operation: suspend () -> T,
    actionOnException: (suspend (exception: Exception?) -> Unit)? = null,
    exceptionMessage: String
): T? {
    return try {
        operation.invoke()
    } catch (e: Exception) {
        //print stack trace
        e.printStackTrace()

        // Log the exception and associated log message
        exceptionMessage.logInline("safeSuspendOperation Exception: ")

        // Invoke the optional actionOnException with the error message
        actionOnException?.invoke(e)
        null
    }
}

/**
 * Safely executes an operation by wrapping it in a try-catch block. The operation to be invoked
 * must have a nullable return type.
 *
 * @param operation The operation to be executed, returning a nullable result.
 * @param exceptionMessage The log message associated with the exception.
 * @param actionOnException An optional action to be executed in case of an exception.
 *                          It takes a nullable string parameter representing the error message.
 * @return The result of the operation or null in case of an exception.
 */
suspend fun <T> safeReturnableOperation(
    operation: suspend () -> T?,
    actionOnException: ((message: Exception?) -> Unit)? = null,
    exceptionMessage: String,
): T? {
    return try {
        operation.invoke()
    } catch (e: Exception) {
        //print stack trace
        e.printStackTrace()

        // Log the exception and associated log message
        exceptionMessage.logInline("safeReturnableOperation Exception: ")

        // Invoke the optional actionOnException with the error message
        actionOnException?.invoke(e)

        // Return null in case of an exception
        null
    }
}


suspend fun <T : Any?> Flow<T>.singleFlowOnItemReceivedInScope(
    onStart: () -> Unit = {},
    onItemReceived: suspend (T) -> Unit = { _ -> },
    onError: (Throwable) -> Unit = { _ -> },
    coroutineScope: CoroutineScope,
) {
    try {
        onStart()
        firstOrNull()?.let { item ->
            coroutineScope.launch {
                try {
                    onItemReceived(item)
                } catch (e: Exception) {
                    onError(e)
                }
            }
        }
    } catch (e: Exception) {
        onError(e)
    }
}