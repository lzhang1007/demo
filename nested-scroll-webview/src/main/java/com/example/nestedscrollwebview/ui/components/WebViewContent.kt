package com.example.nestedscrollwebview.ui.components

import android.webkit.WebView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

/**
 * Compose 中集成 CustomNestedScrollWebView
 */
@Composable
fun WebViewContent(
    modifier: Modifier = Modifier,
    url: String = "https://www.example.com",
    onWebViewCreated: (WebView) -> Unit = {}
) {
    Box(modifier = modifier.fillMaxWidth()) {
        AndroidView(
            factory = { context ->
                CustomNestedScrollWebView(context).apply {
                    settings.apply {
                        javaScriptEnabled = true
                        domStorageEnabled = true
                    }
                    loadUrl(url)
                    onWebViewCreated(this)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}
