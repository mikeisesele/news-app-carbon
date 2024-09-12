package utils


import gradle.kotlin.dsl.accessors._a937729d8a1f305b163059a48f19fa79.api
import gradle.kotlin.dsl.accessors._a937729d8a1f305b163059a48f19fa79.testApi
import gradle.kotlin.dsl.accessors._a937729d8a1f305b163059a48f19fa79.testRuntimeOnly
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