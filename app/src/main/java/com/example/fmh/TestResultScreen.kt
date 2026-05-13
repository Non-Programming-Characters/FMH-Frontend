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
fun TestResultScreen(score: Int, total: Int, onProfile: () -> Unit, onRetry: () -> Unit) {
    val isPassed = score > total / 2

    BoxWithConstraints(modifier = Modifier.fillMaxSize().background(ScreenBgColor)) {
        val scaleW = maxWidth.value / 360f
        val scaleH = maxHeight.value / 640f
        val scaleAvg = (scaleW + scaleH) / 2f

        fun refX(v: Float) = (v * scaleW).dp
        fun refY(v: Float) = (v * scaleH).dp
        fun refW(v: Float) = (v * scaleW).dp
        fun refH(v: Float) = (v * scaleH).dp
        fun refFont(v: Float) = (v * scaleAvg).sp

        @Composable
        fun Modifier.interactiveClick(onClick: () -> Unit) = this.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = LocalIndication.current,
            onClick = onClick
        )

        Text(if (isPassed) "Тест пройден!" else "Тест не пройден!", color = TestTextColor, fontSize = refFont(24f),
            modifier = Modifier.offset(x = refX(104f), y = refY(106f)).size(width = refW(159f), height = refH(28f)))

        Box(modifier = Modifier.offset(x = refX(10f), y = refY(230f)).size(width = refW(335f), height = refH(80f)).background(TestBoxBgColor, shape = RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center) {
            Text("Ваш результат: $score/$total", color = TestTextColor, fontSize = refFont(24f), textAlign = TextAlign.Center)
        }

        val profileBtnColor = if (isPassed) ResultPassButtonBg else ResultButtonBg
        Box(modifier = Modifier.offset(x = refX(235f), y = refY(80f)).size(width = refW(205f), height = refH(50f)).background(profileBtnColor, shape = RoundedCornerShape(10.dp)).interactiveClick(onProfile), contentAlignment = Alignment.Center) {
            Text("Перейти в профиль", color = TestButtonText, fontSize = refFont(16f))
        }

        if (!isPassed) {
            Box(modifier = Modifier.offset(x = refX(77f), y = refY(429f)).size(width = refW(205f), height = refH(50f)).background(ResultButtonBg, shape = RoundedCornerShape(10.dp)).interactiveClick(onRetry), contentAlignment = Alignment.Center) {
                Text("Пройти заново", color = TestButtonText, fontSize = refFont(16f))
            }
        }
    }
}