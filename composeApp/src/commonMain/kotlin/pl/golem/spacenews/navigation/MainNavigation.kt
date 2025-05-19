package pl.golem.spacenews.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pl.golem.spacenews.screen.HomeHost

@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    NavHost(navController, HomeScreen) {

        composable<HomeScreen> {
            HomeHost(navController)
        }

    }
}