# 嵌套滚动 WebView Demo

这是一个完整的 Android Demo 项目，展示了如何实现 Compose + WebView 的无缝嵌套滚动。

## 功能特性

- ✅ **自定义 WebView** - 实现 `NestedScrollingChild3` 接口
- ✅ **智能滚动分配** - 原生组件和 WebView 协调滚动
- ✅ **平滑体验** - 无缝的滚动事件转换
- ✅ **Compose 集成** - 使用 `rememberNestedScrollInteropConnection()`
- ✅ **完整示例** - 包含可运行的 Demo 应用

## 项目结构

```
nested-scroll-webview/
├── src/main/java/com/example/nestedscrollwebview/
│   ├── ui/
│   │   ├── components/
│   │   │   ├── CustomNestedScrollWebView.kt    # 核心 WebView 实现
│   │   │   ├── NestedScrollWebViewInterop.kt   # Compose 互操作
│   │   │   └── WebViewContent.kt               # WebView 组件
│   │   └── screen/
│   │       └── NestedScrollWebViewScreen.kt    # 演示屏幕
│   ├── MainActivity.kt
│   ├── App.kt
│   └── AppTheme.kt
├── src/main/res/
│   ├── values/
│   │   ├── strings.xml
│   │   ├── colors.xml
│   │   └── styles.xml
│   └── assets/
│       └── sample.html
└── build.gradle.kts
```

## 核心实现

### 1. CustomNestedScrollWebView

继承 `WebView` 并实现 `NestedScrollingChild3` 接口：

```kotlin
class CustomNestedScrollWebView : WebView, NestedScrollingChild3 {
    override fun startNestedScroll(axes: Int, type: Int): Boolean
    override fun dispatchNestedPreScroll(..., type: Int): Boolean
    override fun dispatchNestedScroll(..., type: Int): Boolean
    // ... 其他方法
}
```

**核心逻辑**：
- 拦截 `onTouchEvent()` 事件
- 通过 `dispatchNestedPreScroll()` 让父容器优先消费滚动
- WebView 消费剩余滚动量
- 滚到顶部时，通过 `dispatchNestedScroll()` 传递给父容器

### 2. 滚动事件流

```
用户滚动
  ↓
CustomNestedScrollWebView.onTouchEvent()
  ↓
dispatchNestedPreScroll() → 询问父容器
  ↓
父容器(LazyColumn)消费 ↔ WebView 消费剩余
  ↓
WebView 滑到顶部 → dispatchNestedScroll()
  ↓
父容器继续滚动
```

### 3. Compose 集成

```kotlin
@Composable
fun rememberWebViewNestedScrollConnection() = 
    rememberNestedScrollInteropConnection()
```

这个函数返回一个 `NestedScrollConnection`，用于 View 和 Compose 之间的滚动协调。

## 使用方法

### 快速开始

1. **克隆或复制此项目**
2. **打开 Android Studio**
3. **运行应用**

### 在你的项目中使用

#### 步骤 1: 复制 CustomNestedScrollWebView

```kotlin
// 复制 CustomNestedScrollWebView.kt 到你的项目
class CustomNestedScrollWebView : WebView, NestedScrollingChild3 {
    // ... 完整实现
}
```

#### 步骤 2: 在 Compose 中使用

```kotlin
@Composable
fun MyScreen() {
    LazyColumn {
        item {
            // 原生头部
            Header()
        }
        
        item {
            // WebView
            AndroidView(
                factory = { context ->
                    CustomNestedScrollWebView(context).apply {
                        loadUrl("file:///android_asset/content.html")
                    }
                }
            )
        }
    }
}
```

#### 步骤 3: 添加依赖

```gradle
dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.compose.ui:ui:1.6.0")
    implementation("androidx.webkit:webkit:1.7.0")
}
```

## 技术细节

### NestedScrollingChild3 接口

Android 5.0 (API 21) 引入了嵌套滚动机制，API 29 添加了 `NestedScrollingChild3`：

```kotlin
interface NestedScrollingChild3 {
    fun startNestedScroll(axes: Int, type: Int): Boolean
    fun stopNestedScroll(type: Int)
    fun dispatchNestedPreScroll(..., type: Int): Boolean
    fun dispatchNestedScroll(..., type: Int): Boolean
}
```

### 关键方法

| 方法 | 说明 |
|------|------|
| `startNestedScroll()` | 开始嵌套滚动 |
| `stopNestedScroll()` | 停止嵌套滚动 |
| `dispatchNestedPreScroll()` | 先让父容器处理滚动 |
| `dispatchNestedScroll()` | 通知父容器剩余的滚动量 |
| `dispatchNestedFling()` | 处理快速滑动 |

## 应用场景

- 📱 **App Store 详情页** - 应用头部 + WebView 详介
- 📰 **新闻阅读器** - 原生新闻列表 + WebView 内容
- 🛍️ **电商应用** - 商品信息 + WebView 描述
- 📚 **文档查看器** - 目录导航 + WebView 内容
- 🌐 **混合应用** - 任何需要混合原生和 Web 内容的场景

## 调试技巧

### 查看滚动事件

在 `CustomNestedScrollWebView` 中添加日志：

```kotlin
override fun onTouchEvent(event: MotionEvent): Boolean {
    Log.d("ScrollEvent", "Action: ${event.actionMasked}, Y: ${event.y}")
    return super.onTouchEvent(event)
}
```

### 检查嵌套滚动状态

```kotlin
Log.d("NestedScroll", "Has parent: ${hasNestedScrollingParent()}")
Log.d("NestedScroll", "WebView scrollY: ${scrollY}")
```

## 性能优化

1. **启用硬件加速** - 在 manifest 中配置
2. **优化 WebView 加载** - 预加载常用页面
3. **缓存策略** - 合理使用 WebView 缓存
4. **内存管理** - 及时清理 WebView 资源

## 常见问题

**Q: WebView 滚动时卡顿？**
A: 检查 JavaScript 执行是否占用主线程。使用 `webView.evaluateJavascript()` 而不是同步调用。

**Q: 如何处理外层和内层都可以滚动的情况？**
A: 通过 `dispatchNestedPreScroll()` 让外层优先消费，WebView 处理剩余部分。

**Q: 支持哪些 Android 版本？**
A: API 21+ (Android 5.0+)，建议 minSdk = 24 (Android 7.0+)。

## 参考资源

- [NestedScrollingChild3 官方文档](https://developer.android.com/reference/androidx/core/view/NestedScrollingChild3)
- [Compose 官方示例 - Jetchat](https://github.com/android/compose-samples/tree/main/Jetchat)
- [NestedScrollView 源码分析](https://developer.android.com/reference/androidx/core/widget/NestedScrollView)

## 许可证

MIT License

## 贡献

欢迎提交 Issue 和 Pull Request！
