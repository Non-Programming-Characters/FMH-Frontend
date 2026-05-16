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
    data class MatDetail(val title: String) : AppRoute()
    object Test : AppRoute()
    data class Result(val score: Int) : AppRoute()
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var agreed by remember { mutableStateOf(false) }
            var route by remember { mutableStateOf<AppRoute>(AppRoute.Home) }

            // Главный экран всегда рендерится, но затемняется оверлеем до согласия
            when (val r = route) {
                AppRoute.Home -> MainScreen(
                    activeScreen = "home",
                    onTabChange = {
                        when (it) {
                            "home" -> route = AppRoute.Home
                            "materials" -> route = AppRoute.Materials
                            "profile" -> route = AppRoute.Profile
                        }
                    },
                    onCardClick = { route = AppRoute.Card(it) },
                    showAgreement = { !agreed },
                    onAgree = { agreed = true }
                )
                AppRoute.Materials -> MaterialsScreen(
                    activeScreen = "materials",
                    onTabChange = {
                        when (it) {
                            "home" -> route = AppRoute.Home
                            "materials" -> route = AppRoute.Materials
                            "profile" -> route = AppRoute.Profile
                        }
                    },
                    onMaterialClick = { route = AppRoute.MatDetail("Материал $it") }
                )
                AppRoute.Profile -> ProfileScreen(
                    activeScreen = "profile",
                    onTabChange = {
                        when (it) {
                            "home" -> route = AppRoute.Home
                            "materials" -> route = AppRoute.Materials
                            "profile" -> route = AppRoute.Profile
                        }
                    },
                    onStartTest = { route = AppRoute.Test }
                )
                is AppRoute.Card -> CardScreen(
                    title = r.title,
                    onBack = { route = AppRoute.Home }
                )
                is AppRoute.MatDetail -> MaterialDetailScreen(
                    title = r.title,
                    onBack = { route = AppRoute.Materials }
                )
                AppRoute.Test -> TestScreen(
                    onBack = { route = AppRoute.Profile },
                    onFinish = { route = AppRoute.Result(it) }
                )
                is AppRoute.Result -> TestResultScreen(
                    score = r.score,
                    total = 15,
                    onProfile = { route = AppRoute.Profile },
                    onRetry = { route = AppRoute.Test }
                )
            }
        }
    }
}