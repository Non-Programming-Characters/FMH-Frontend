package com.example.fmh

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainScreen(activeScreen: String, onTabChange: (String) -> Unit, onCardClick: (String) -> Unit) {
    var searchQuery by remember { mutableStateOf("") }

    BoxWithConstraints(modifier = Modifier.fillMaxSize().background(ScreenBgColor)) {
        val scaleW = maxWidth.value / 360f
        val scaleH = maxHeight.value / 640f
        val scaleAvg = (scaleW + scaleH) / 2f

        fun refX(v: Float) = (v * scaleW).dp
        fun refY(v: Float) = (v * scaleH).dp
        fun refW(v: Float) = (v * scaleW).dp
        fun refH(v: Float) = (v * scaleH).dp
        fun refSquare(v: Float) = (v * scaleAvg).dp
        fun refFont(v: Float) = (v * scaleAvg).sp

        @Composable
        fun Modifier.interactiveClick(onClick: () -> Unit) = this.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = LocalIndication.current,
            onClick = onClick
        )

        Box(
            modifier = Modifier.offset(x = refX(16f), y = refY(42f)).size(width = refW(328f), height = refH(44f))
                .background(SearchBarBgColor, shape = RoundedCornerShape(percent = 50)).interactiveClick { },
            contentAlignment = Alignment.Center
        ) {
            Box(modifier = Modifier.align(Alignment.TopStart).offset(x = refX(25f), y = refY(8f)).size(width = refW(255f), height = refH(28f))) {
                BasicTextField(value = searchQuery, onValueChange = { searchQuery = it }, modifier = Modifier.fillMaxSize().background(Color.Transparent),
                    textStyle = TextStyle(color = IconColorLight, fontSize = refFont(20f), textAlign = TextAlign.Center), singleLine = true,
                    decorationBox = { inner -> Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                        if (searchQuery.isEmpty()) Text("Поиск...", color = IconColorLight, fontSize = refFont(20f), textAlign = TextAlign.Center)
                        inner()
                    }})
            }
            Icon(Icons.Default.Search, null, tint = IconColorLight, modifier = Modifier.align(Alignment.TopStart).offset(x = refX(283f), y = refY(12f)).size(refSquare(20f)).interactiveClick {})
        }

        LazyColumn(state = rememberLazyListState(), modifier = Modifier.offset(y = refY(109f)).height(refY(544f) - refY(109f)).fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(refH(10f))) {
            items(15) { idx ->
                Box(modifier = Modifier.fillMaxWidth().height(refH(75f)).background(ListItemBgColor).interactiveClick { onCardClick("Заголовок карточки #${idx + 1}") }) {
                    Box(modifier = Modifier.align(Alignment.TopStart).offset(x = refX(25f), y = refY(10f)).size(refSquare(55f)).background(ListItemImageColor))
                    Text("Заголовок ${idx + 1}", color = BottomBarBgColor, fontSize = refFont(16f), modifier = Modifier.align(Alignment.TopStart).offset(x = refX(99f), y = refY(18f)).size(width = refW(236f), height = refH(19f)))
                    Text("Описание элемента списка", color = SearchBarBgColor, fontSize = refFont(12f), modifier = Modifier.align(Alignment.TopStart).offset(x = refX(99f), y = refY(42f)).size(width = refW(236f), height = refH(14f)))
                }
            }
        }

        Box(modifier = Modifier.offset(x = refX(274f), y = refY(434f)).size(refSquare(70f)).background(CallButtonBgColor, shape = CircleShape).interactiveClick { /* Вызов */ }, contentAlignment = Alignment.Center) {
            Icon(Icons.Default.Phone, null, tint = IconColorLight, modifier = Modifier.size(refSquare(30f)))
        }

        BottomNavigationBar(activeScreen, onTabChange, { refX(it) }, { refY(it) }, { refW(it) }, { refH(it) }, { refSquare(it) })
    }
}