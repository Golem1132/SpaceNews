package pl.golem.spacenews.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun WebView(modifier: Modifier, url: String)