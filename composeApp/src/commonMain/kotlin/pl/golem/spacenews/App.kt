package pl.golem.spacenews

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview
import pl.golem.spacenews.navigation.SpaceNewsNavigation

@Composable
@Preview
fun App() {
    Surface(modifier = Modifier.fillMaxSize()) {
        SpaceNewsNavigation()
    }
}