package com.michael.baseapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDeepLink

sealed interface ScreenSpec {

    companion object {
        val allScreens = listOf<ScreenSpec>(
            HomeScreenSpec,
        )
    }

    val route: String

    val icon: ImageVector?
        get() = null
    val arguments: List<NamedNavArgument> get() = emptyList()

    val deepLinks: List<NavDeepLink> get() = emptyList()

    @Composable
    fun Content(
        navController: NavController,
        navBackStackEntry: NavBackStackEntry,
    )
}
