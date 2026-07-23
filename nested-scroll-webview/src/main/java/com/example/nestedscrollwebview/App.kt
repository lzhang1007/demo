package com.example.nestedscrollwebview

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.nestedscrollwebview.ui.screen.NestedScrollWebViewScreen

@Composable
fun App() {
    NestedScrollWebViewTheme {
        NestedScrollWebViewScreen(modifier = Modifier.fillMaxSize())
    }
}
