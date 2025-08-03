package pl.golem.spacenews

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.window.ComposeUIViewController
import pl.golem.spacenews.di.initializeKoin

val LocalNativeViewFactory = staticCompositionLocalOf<NativeViewFactory> {
    error("eh")
}

fun MainViewController(
    nativeViewFactory: NativeViewFactory
) = ComposeUIViewController(
    configure = { initializeKoin() })
{
    CompositionLocalProvider(LocalNativeViewFactory provides nativeViewFactory) {
        App()
    }
}