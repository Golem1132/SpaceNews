package pl.golem.spacenews

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview
import pl.golem.spacenews.navigation.SpaceNewsNavigation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() {
    Surface(modifier = Modifier.fillMaxSize()) {
        SpaceNewsNavigation()
    }
}