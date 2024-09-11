package com.michael.testing

import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest

open class BaseTest {

    private val testScope: TestScope = TestScope()
    private val testDispatcher = StandardTestDispatcher(testScope.testScheduler)
    protected val dispatcherProvider = TestDispatcherProvider(testDispatcher)

    /**
     * Executes `runTest` which launches a coroutine to run tests on any suspended functions
     * or coroutines. All coroutine testing that is performed must run on the same
     * `TestCoroutineScheduler` in order to accurately track time. Therefore we need to ensure
     * that we run the test on the defined `TestScope` which uses the same `TestCoroutineScheduler`
     * that is used to create our `StandardTestDispatcher`
     */
    fun startTest(
        testBody: suspend TestScope.() -> Unit,
    ) {
        testScope.runTest(testBody = testBody)
    }
}
