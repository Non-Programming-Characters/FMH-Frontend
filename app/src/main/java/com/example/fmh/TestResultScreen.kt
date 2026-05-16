package com.example.fmh

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TestResultScreen(
    score: Int,
    total: Int,
    onProfile: () -> Unit,
    onRetry: () -> Unit
) {
    val passed = score > total / 2

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize().background(ScreenBgColor)
    ) {
        val scaleW = maxWidth.value / 360f
        val scaleH = maxHeight.value / 640f

        fun refX(v: Float) = (v * scaleW).dp
        fun refY(v: Float) = (v * scaleH).dp
        fun refW(v: Float) = (v * scaleW).dp
        fun refH(v: Float) = (v * scaleH).dp
        fun refFont(v: Float) = (v * scaleW).sp

        @Composable
        fun Modifier.interactiveClick(onClick: () -> Unit): Modifier = this.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = LocalIndication.current,
            onClick = onClick
        )

        Text(
            text = if (passed) "Тест пройден!" else "Тест не пройден!",
            color = TestTextColor,
            fontSize = refFont(24f),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .offset(y = refY(106f))
                .fillMaxWidth()
        )

        Box(
            modifier = Modifier
                .offset(x = refX(10f), y = refY(230f))
                .size(width = refW(335f), height = refH(80f))
                .background(TestBoxBgColor),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Ваш результат: $score/$total",
                color = TestTextColor,
                fontSize = refFont(24f),
                textAlign = TextAlign.Center
            )
        }

        Box(
            modifier = Modifier
                .offset(x = refX(77f), y = refY(490f))
                .size(width = refW(205f), height = refH(50f))
                .background(if (passed) ResultPassButtonBg else ResultButtonBg)
                .interactiveClick(onProfile),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Перейти в профиль",
                color = TestButtonText,
                fontSize = refFont(16f)
            )
        }

        if (!passed) {
            Box(
                modifier = Modifier
                    .offset(x = refX(77f), y = refY(429f))
                    .size(width = refW(205f), height = refH(50f))
                    .background(ResultButtonBg)
                    .interactiveClick(onRetry),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Пройти заново",
                    color = TestButtonText,
                    fontSize = refFont(16f)
                )
            }
        }
    }
}