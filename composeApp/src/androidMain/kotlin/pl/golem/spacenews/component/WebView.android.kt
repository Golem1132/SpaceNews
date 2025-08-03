package pl.golem.spacenews.component

import android.webkit.WebView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
actual fun WebView(modifier: Modifier, url: String) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            WebView(context)
        },
        update = {
            it.loadUrl(url)
        }

    )
}