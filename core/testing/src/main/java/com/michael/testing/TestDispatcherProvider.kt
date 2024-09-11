package com.michael.testing

import com.michael.base.providers.DispatcherProvider
import kotlinx.coroutines.test.TestDispatcher

class TestDispatcherProvider(
    val testDispatcher: TestDispatcher,
) : DispatcherProvider() {
    override val main = testDispatcher
    override val default = testDispatcher
    override val io = testDispatcher
}
