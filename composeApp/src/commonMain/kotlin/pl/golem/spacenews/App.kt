package pl.golem.spacenews

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview
import pl.golem.spacenews.navigation.MainNavigation
import pl.golem.spacenews.screen.articles.ArticlesScreen

@Composable
@Preview
fun App() {
    Surface(modifier = Modifier.fillMaxSize()) {
        ArticlesScreen()
    }

}