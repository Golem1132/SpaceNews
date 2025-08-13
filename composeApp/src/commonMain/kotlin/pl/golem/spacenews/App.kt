package pl.golem.spacenews

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview
import pl.golem.spacenews.navigation.SpaceNewsNavigation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() {
    Surface(modifier = Modifier.fillMaxSize()) {
        /*Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text("Articles")
                    }
                )
            }
        ) {
            PrimaryTabRow(
                modifier = Modifier.fillMaxWidth(),
                selectedTabIndex = 0
            ) {
                Tab(selected = true, onClick = {}) { Text("Articles") }
                Tab(selected = false, onClick = {}) { Text("Blogs") }
                Tab(selected = false, onClick = {}) { Text("Reports") }
            }
//            SpaceNewsNavigation()
        }*/
                    SpaceNewsNavigation()
    }
}