package com.example.nestedscrollwebview.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection

/**
 * Compose 中使用自定义 WebView 的互操作组件
 * 支持与 LazyColumn/Column 的嵌套滚动
 */
@Composable
fun rememberWebViewNestedScrollConnection(): NestedScrollConnection {
    return rememberNestedScrollInteropConnection()
}

/**
 * 为 Compose 布局添加嵌套滚动支持
 */
fun Modifier.webViewNestedScroll(
    nestedScrollConnection: NestedScrollConnection
): Modifier = this.nestedScroll(nestedScrollConnection)
