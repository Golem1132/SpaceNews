package pl.golem.spacenews

import androidx.compose.ui.window.ComposeUIViewController
import pl.golem.spacenews.di.initializeKoin

fun MainViewController() = ComposeUIViewController(
    configure = { initializeKoin() })
{ App() }