package pl.golem.spacenews

import platform.UIKit.UIViewController

interface NativeViewFactory {

    fun createNativeWebView(url: String): UIViewController
}