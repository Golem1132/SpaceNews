import ComposeApp
import SwiftUI

class IosNativeViewFactory: NativeViewFactory {
    static var shared = IosNativeViewFactory()
    func createNativeWebView(url: String) -> UIViewController {
        let view = NativeWebView(url: url)
        return UIHostingController(rootView: view)
    }
}
