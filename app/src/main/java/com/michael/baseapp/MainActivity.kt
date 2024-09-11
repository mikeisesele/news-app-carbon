package com.michael.baseapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.navigation.compose.rememberNavController
import com.michael.baseapp.mainscreen.presentation.MainScreen
import com.michael.baseapp.navigation.Navigator
import com.michael.ui.extensions.rememberStateWithLifecycle
import com.michael.ui.theme.BaseAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            BaseAppTheme {
                Navigator(navController = navController)

            }
        }
    }
}