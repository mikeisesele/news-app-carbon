package com.michael.baseapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.michael.common.ImmutableList
import com.michael.home.HomeScreenDestination


@Composable
fun Navigator(
    navController: NavHostController,
) {
    NavHost(navController = navController, startDestination = HomeScreenDestination) {
        composable<HomeScreenDestination> {

        }

        fun handleEventScreenBackClick(navController: NavHostController) {
            if (navController.previousBackStackEntry == null) {
                // No previous back stack entry, navigate to home screen
                navController.navigate(HomeScreenDestination) {
                    // Clear back stack to prevent going back to the event screen
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = false
                    }
                }
            } else {
                // Navigate up in the stack
                navController.navigateUp()
            }
        }

        fun handleSystemBackClick(
            navController: NavHostController,
            topLevelDestinations: ImmutableList<TopLevelDestination>,
        ) {
            val cleanedCurrentRoute = navController.currentDestination?.route?.cleanRoute()
            val cleanedTopLevelRoutes =
                topLevelDestinations.map { it.destination.toString().cleanRoute() }

            if (cleanedCurrentRoute in cleanedTopLevelRoutes) {
                if (cleanedCurrentRoute?.cleanRoute() == EventFeedScreenDestination.toString()
                        .cleanRoute()
                ) {
                    navController.popBackStack()
                } else {
                    navController.navigate(EventFeedScreenDestination) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                }
            } else {
                navController.navigateUp()
            }
        }
    }
