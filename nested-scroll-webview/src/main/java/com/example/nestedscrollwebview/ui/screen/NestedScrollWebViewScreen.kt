package com.example.nestedscrollwebview.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nestedscrollwebview.ui.components.WebViewContent
import com.example.nestedscrollwebview.ui.components.rememberWebViewNestedScrollConnection

/**
 * 完整的嵌套滚动 WebView 演示
 * 
 * 布局结构：
 * ┌─────────────────────────────┐
 * │   原生 Header (可滚动)       │  <- 由外层 LazyColumn 控制
 * ├─────────────────────────────┤
 * │        WebView              │  <- 内层 WebView，支持嵌套滚动
 * │    (可独立滚动，滚到       │
 * │     顶部时交给外层)         │
 * └─────────────────────────────┘
 */
@Composable
fun NestedScrollWebViewScreen(modifier: Modifier = Modifier) {
    val nestedScrollConnection = rememberWebViewNestedScrollConnection()
    
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Header 部分
        item {
            NativeHeader()
        }

        // 原生内容卡片
        item {
            ContentCard(
                title = "功能介绍",
                content = """
                    这个 Demo 展示了如何实现 WebView 与 Compose NestedScrollView 的完美适配：
                    
                    • 向下滚动时，外层容器先滚动
                    • 当外层滚动至底部时，控制交给 WebView
                    • 当 WebView 滚到顶部时，控制再交回外层
                    • 完全支持连贯的滚动体验
                """.trimIndent()
            )
        }

        // 技术栈信息
        item {
            ContentCard(
                title = "技术实现",
                content = """
                    核心技术：
                    • NestedScrollingChild3 - WebView 作为嵌套滚动子视图
                    • NestedScrollConnection - Compose 与 View 的滚动协调
                    • TouchEvent 拦截 - 精确控制滚动事件流向
                    • AndroidView - 在 Compose 中集成原生 WebView
                """.trimIndent()
            )
        }

        // WebView 部分
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .padding(16.dp)
            ) {
                Text(
                    text = "WebView 区域",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                
                WebViewContent(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp),
                    url = "file:///android_asset/sample.html"
                )
            }
        }

        // 底部更多内容
        item {
            ContentCard(
                title = "使用场景",
                content = """
                    适用于以下场景：
                    • 混合原生 UI 和 Web 内容的应用
                    • 需要完整滚动体验的列表页面
                    • 在原生 Compose UI 中嵌入 WebView
                    • App 详情页（标题/评分 + WebView 介绍）
                """.trimIndent()
            )
        }

        item {
            Box(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun NativeHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "嵌套滚动 WebView Demo",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = "Compose + WebView 完美适配",
                fontSize = 12.sp,
                color = Color.White.copy(alpha = 0.7f),
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
private fun ContentCard(
    title: String,
    content: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF5F5F5)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            
            Text(
                text = content,
                fontSize = 13.sp,
                color = Color.Gray,
                lineHeight = 18.sp
            )
        }
    }
}
