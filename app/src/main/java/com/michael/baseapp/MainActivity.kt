package com.michael.baseapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.compose.rememberNavController
import com.michael.base.contract.ViewEvent
import com.michael.base.model.Ignored
import com.michael.baseapp.mainscreen.contract.MainSideEffect
import com.michael.baseapp.mainscreen.presentation.MainScreen
import com.michael.baseapp.navigation.NavigationGraph
import com.michael.baseapp.navigation.ScreenSpec
import com.michael.baseapp.navigation.processNavigation
import com.michael.ui.extensions.collectAsEffect
import com.michael.ui.extensions.rememberStateWithLifecycle
import com.michael.ui.theme.BaseAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainScreenViewModel: MainScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val state by rememberStateWithLifecycle(mainScreenViewModel.state)

            subscribeToSideEffects(
                events = mainScreenViewModel::events,
                navigateToDestinations = {
                    navController.processNavigation(it)
                },
            )

            BaseAppTheme {
                MainScreen(state) {
                    NavigationGraph(navController)
                }
            }
        }
    }
}

@Suppress("composableNaming")
@Composable
private fun subscribeToSideEffects(
    events: () -> Flow<ViewEvent>,
    navigateToDestinations: (ScreenSpec) -> Unit,
) {
    events().collectAsEffect { viewEvent ->
        when (viewEvent) {
            is ViewEvent.Effect -> when (val target = viewEvent.effect) {
                is MainSideEffect.NavigateToDestination -> {
                    navigateToDestinations(target.screenSpec)
                }
            }

            else -> Ignored
        }
    }
}
