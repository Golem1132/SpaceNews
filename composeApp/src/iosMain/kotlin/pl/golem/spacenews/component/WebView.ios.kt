package pl.golem.spacenews.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitViewController
import pl.golem.spacenews.LocalNativeViewFactory

@Composable
actual fun WebView(modifier: Modifier, url: String) {
    val factory = LocalNativeViewFactory.current
    UIKitViewController(
        modifier = modifier,
        factory = {
            factory.createNativeWebView(url)
        }
    )
}