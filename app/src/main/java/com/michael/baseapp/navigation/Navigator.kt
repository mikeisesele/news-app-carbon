package com.michael.baseapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.michael.common.ImmutableList
import com.michael.news.presentation.NewsFeedScreen
import com.michael.news.presentation.NewsFeedScreenDestination
import com.michael.newsdetail.presentation.NewsDetailScreen
import com.michael.newsdetail.presentation.NewsDetailScreenDestination


@Composable
fun Navigator(
    navController: NavHostController,
) {
    NavHost(navController = navController, startDestination = NewsFeedScreenDestination) {
        composable<NewsFeedScreenDestination> {
            NewsFeedScreen(onBackClick = navController::navigateUp, onNewsCardClick = {
                navController.navigate(NewsDetailScreenDestination(it))
            })
        }


        composable<NewsDetailScreenDestination> {
            val args = it.toRoute<NewsDetailScreenDestination>()
           NewsDetailScreen(args.newsId, onBackClick = { navController.popBackStack() })
        }
    }

}