package com.example.fmh

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfileScreen(activeScreen: String, onTabChange: (String) -> Unit, onStartTest: () -> Unit) {
    BoxWithConstraints(modifier = Modifier.fillMaxSize().background(ProfileBgColor)) {
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

        val infoBlockY = refY(66f)
        val infoBlockHeight = refH(90f)
        Box(modifier = Modifier.offset(x = refX(15f), y = infoBlockY).size(width = refW(330f), height = infoBlockHeight).background(InfoBlockBgColor, shape = RoundedCornerShape(12.dp))) {
            Text("UserLogin_123", color = ProfileTextColor, fontSize = refFont(24f), modifier = Modifier.offset(x = refX(11f), y = refY(17f)).widthIn(min = refW(107f), max = refW(330f) - refX(26f)))
            Text("Дата регистрации: 10.03.2026", color = ProfileTextColor, fontSize = refFont(16f), modifier = Modifier.offset(x = refX(11f), y = refY(57f)).widthIn(min = refW(225f), max = refW(330f) - refX(26f)))
        }

        Box(modifier = Modifier.offset(x = refX(113f), y = refY(194f)).size(width = refW(130f), height = refH(45f)).background(ProfileButtonBgColor, shape = RoundedCornerShape(8.dp)).interactiveClick(onStartTest), contentAlignment = Alignment.Center) {
            Text("Пройти тест", color = ProfileButtonTextColor, fontSize = refFont(16f))
        }

        Text("История тестов", color = ProfileTextColor, fontSize = refFont(16f), modifier = Modifier.offset(x = refX(24f), y = refY(277f)).size(width = refW(117f), height = refH(19f)))

        Column(modifier = Modifier.offset(x = refX(10f), y = refY(330f)).fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(refH(10f))) {
            listOf(1, 2).forEach { testNum ->
                Box(modifier = Modifier.fillMaxWidth().height(refH(80f)).background(TestBlockBgColor, shape = RoundedCornerShape(10.dp))) {
                    Text("Тест $testNum", color = ProfileTextColor, fontSize = refFont(16f), modifier = Modifier.offset(x = refX(24f), y = refY(24f)).size(width = refW(47f), height = refH(19f)))
                    Text("Результат: ${10 + testNum}/15", color = ProfileTextColor, fontSize = refFont(14f), modifier = Modifier.offset(x = refX(24f), y = refY(42f)).size(width = refW(110f), height = refH(16f)))
                    Text("${15 + testNum}.03.2026", color = ProfileTextColor, fontSize = refFont(14f), textAlign = TextAlign.End, modifier = Modifier.offset(x = refX(256f), y = refY(24f)).size(width = refW(71f), height = refH(16f)))
                }
            }
        }

        BottomNavigationBar(activeScreen, onTabChange, { refX(it) }, { refY(it) }, { refW(it) }, { refH(it) }, { refSquare(it) })
    }
}