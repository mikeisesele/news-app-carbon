package com.michael.baseapp.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = HomeScreenSpec.route,
    ) {
        ScreenSpec.allScreens.forEach { screen ->
            composable(
                screen.route,
                screen.arguments,
                screen.deepLinks,
            ) {
                screen.Content(navController, it)
            }
        }
    }
}

@SuppressLint("RestrictedApi")
fun NavHostController.processNavigation(screen: ScreenSpec) {
    val doesStackContainDestination = currentBackStack.value.find { backStack ->
        backStack.destination.route != null && backStack.destination.route!!.contains(screen.route)
    } != null

    if (!doesStackContainDestination && screen != HomeScreenSpec) {
        navigate(screen.route)
    } else if (doesStackContainDestination) {
        navigate(screen.route) {
            popUpTo(screen.route) {
                inclusive = false
            }
        }
    }
}
