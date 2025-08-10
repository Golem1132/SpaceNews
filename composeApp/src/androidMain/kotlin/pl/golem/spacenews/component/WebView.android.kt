package pl.golem.spacenews.component

import android.webkit.WebView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView


class WebViewManager() {
    var webView: WebView? = null
    private set

    fun goBack() {
        webView?.takeIf { it.canGoBack() }?.goBack()
    }
    internal fun registerWebView(webViewInstance: WebView) {
        webView = webViewInstance
    }
}

@Composable
fun rememberWebViewManager(): WebViewManager {
    return remember { WebViewManager() }
}

@Composable
actual fun WebView(modifier: Modifier, url: String) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            WebView(context).apply {
                loadUrl(url)
            }
        },
        update = {}

    )
}