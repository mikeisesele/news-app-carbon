package com.michael.baseapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.michael.home.HomeScreen

object HomeScreenSpec : ScreenSpec {

    override val route = "home"

    override val arguments = emptyList<NamedNavArgument>()

    @Composable
    override fun Content(
        navController: NavController,
        navBackStackEntry: NavBackStackEntry,
    ) {
        HomeScreen()
    }
}

// object HomeScreenSpec: ScreenSpec {
//
//    override val route = Destination.Home.title
//
//    override val arguments = listOf(
//        navArgument("id") {
//            type = NavType.StringType
//        },
//        navArgument("isEditable") {
//            type = NavType.BoolType
//            defaultValue = false
//        }
//    )
//
//    @Composable
//    override fun Content(
//        navController: NavController,
//        navBackStackEntry: NavBackStackEntry,
//    ) {
//        ProfileScreen(
//            profileId = navBackStackEntry.arguments?.getString("id")!!,
//            isEditable = navBackStackEntry.arguments?.getBoolean("isEditable")!!
//        )
//    }
// }
