package com.example.fmh

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun BottomNavigationBar(
    activeScreen: String,
    onTabChange: (String) -> Unit,
    refX: (Float) -> Dp,
    refY: (Float) -> Dp,
    refW: (Float) -> Dp,
    refH: (Float) -> Dp,
    refSquare: (Float) -> Dp
) {
    @Composable
    fun Modifier.interactiveClick(onClick: () -> Unit) = this.clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = LocalIndication.current,
        onClick = onClick
    )

    val homeColor = if (activeScreen == "home") ActiveIconColor else InactiveIconColor
    val bookColor = if (activeScreen == "materials") ActiveIconColor else InactiveIconColor
    val personColor = if (activeScreen == "profile") ActiveIconColor else InactiveIconColor

    Box(
        modifier = Modifier
            .offset(y = refY(545f))
            .size(width = refW(360f), height = refH(75f))
            .background(BottomBarBgColor)
    ) {
        Icon(Icons.Default.Home, "Главная", tint = homeColor, modifier = Modifier.align(Alignment.TopStart).offset(x = refX(45f), y = refY(17f)).size(refSquare(40f)).interactiveClick { onTabChange("home") })
        Icon(Icons.AutoMirrored.Filled.MenuBook, "Материалы", tint = bookColor, modifier = Modifier.align(Alignment.TopStart).offset(x = refX(160f), y = refY(17f)).size(refSquare(40f)).interactiveClick { onTabChange("materials") })
        Icon(Icons.Default.Person, "Профиль", tint = personColor, modifier = Modifier.align(Alignment.TopStart).offset(x = refX(275f), y = refY(17f)).size(refSquare(40f)).interactiveClick { onTabChange("profile") })
    }
}