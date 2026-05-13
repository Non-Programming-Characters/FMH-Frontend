package com.example.fmh

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*

sealed class AppRoute {
    object Home : AppRoute()
    object Materials : AppRoute()
    object Profile : AppRoute()
    data class Card(val title: String) : AppRoute()
    data class MaterialDetail(val title: String) : AppRoute()
    object Test : AppRoute()
    data class TestResult(val score: Int) : AppRoute()
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isAgreementAccepted by remember { mutableStateOf(false) }
            var currentRoute by remember { mutableStateOf<AppRoute>(AppRoute.Home) }

            if (!isAgreementAccepted) {
                AgreementScreen(onAgree = { isAgreementAccepted = true; currentRoute = AppRoute.Home })
            } else {
                when (val route = currentRoute) {
                    AppRoute.Home -> MainScreen(activeScreen = "home", onTabChange = { currentRoute = when(it) { "home" -> AppRoute.Home; "materials" -> AppRoute.Materials; "profile" -> AppRoute.Profile; else -> AppRoute.Home } }, onCardClick = { currentRoute = AppRoute.Card(it) })
                    AppRoute.Materials -> MaterialsScreen(activeScreen = "materials", onTabChange = { currentRoute = when(it) { "home" -> AppRoute.Home; "materials" -> AppRoute.Materials; "profile" -> AppRoute.Profile; else -> AppRoute.Home } }, onMaterialClick = { currentRoute = AppRoute.MaterialDetail("Материал $it") })
                    AppRoute.Profile -> ProfileScreen(activeScreen = "profile", onTabChange = { currentRoute = when(it) { "home" -> AppRoute.Home; "materials" -> AppRoute.Materials; "profile" -> AppRoute.Profile; else -> AppRoute.Home } }, onStartTest = { currentRoute = AppRoute.Test })
                    is AppRoute.Card -> CardScreen(title = route.title, onBack = { currentRoute = AppRoute.Home })
                    is AppRoute.MaterialDetail -> MaterialDetailScreen(title = route.title, onBack = { currentRoute = AppRoute.Materials })
                    AppRoute.Test -> TestScreen(onBack = { currentRoute = AppRoute.Profile }, onFinish = { currentRoute = AppRoute.TestResult(it) })
                    is AppRoute.TestResult -> TestResultScreen(score = route.score, total = 15, onProfile = { currentRoute = AppRoute.Profile }, onRetry = { currentRoute = AppRoute.Test })
                }
            }
        }
    }
}