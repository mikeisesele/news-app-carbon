package utils

import gradle.kotlin.dsl.accessors._2983b8eb6a448082a49ee3cd9c53d2a6.api
import gradle.kotlin.dsl.accessors._2983b8eb6a448082a49ee3cd9c53d2a6.testApi
import gradle.kotlin.dsl.accessors._2983b8eb6a448082a49ee3cd9c53d2a6.testRuntimeOnly
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.kotlin.dsl.DependencyHandlerScope

fun DependencyHandlerScope.providetestDependencies(libs: LibrariesForLibs) {
    with(this) {
        testApi(libs.junit)
        testRuntimeOnly(libs.junit.engine)
        api(libs.androidx.junit.ktx)
        testApi(libs.turbine)
        testApi(libs.mockk)
        testApi(libs.androidx.core.testing)
        testApi(libs.coroutines.test)
        testApi(libs.kotest.assertions)
    }
}