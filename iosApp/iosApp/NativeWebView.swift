//
//  NativeWebView.swift
//  iosApp
//
//  Created by Adam Hałas on 27/07/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import WebKit

struct NativeWebView: UIViewRepresentable {
    var url: String
    func makeUIView(context: Context) -> WKWebView {
        let webView = WKWebView()
        let request = URLRequest(url: URL(string: url)!)
        webView.load(request)
        return webView
    }
    
    func updateUIView(_ uiView: UIViewType, context: Context) {
        
    }
}

#Preview {
    NativeWebView(url: "https://www.apple.com")
}
