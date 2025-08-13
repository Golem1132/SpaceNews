package pl.golem.spacenews.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import pl.golem.spacenews.screen.articles.ArticlesScreen
import pl.golem.spacenews.screen.main.MainScreen
import pl.golem.spacenews.screen.web.WebScreen


@Composable
fun SpaceNewsNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Main) {

        composable<Main> {
            MainScreen(navController)
        }

        composable<WebScreen> { backstack ->
            val route: WebScreen = backstack.toRoute()
            WebScreen(navController, route.url)
        }
    }
}