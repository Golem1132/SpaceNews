package pl.golem.spacenews.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import pl.golem.spacenews.component.WebView
import pl.golem.spacenews.screen.articles.ArticlesScreen


@Composable
fun SpaceNewsNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Articles) {
        composable<Articles> {
            ArticlesScreen(navController)
        }

        composable<WebView> { backstack ->
            val route: WebView = backstack.toRoute()
            WebView(Modifier.fillMaxSize(), route.url)
        }
    }
}