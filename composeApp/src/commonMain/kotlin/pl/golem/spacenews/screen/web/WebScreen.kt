package pl.golem.spacenews.screen.web

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import pl.golem.spacenews.component.WebView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WebScreen(navController: NavController, url: String) {
    val webViewState = remember {

    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        "",
                        modifier = Modifier.clickable {
                            navController.popBackStack()
                        })
                }
            )
        }
    ) {
        WebView(modifier = Modifier.fillMaxSize().padding(it), url)
    }
}