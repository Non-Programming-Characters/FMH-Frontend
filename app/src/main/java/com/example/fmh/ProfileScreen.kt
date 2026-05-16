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
fun ProfileScreen(
    activeScreen: String,
    onTabChange: (String) -> Unit,
    onStartTest: () -> Unit
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(ProfileBgColor)
    ) {
        val scaleW = maxWidth.value / 360f
        val scaleH = maxHeight.value / 640f
        val scaleAvg = (scaleW + scaleH) / 2f

        fun refX(v: Float) = (v * scaleW).dp
        fun refY(v: Float) = (v * scaleH).dp
        fun refW(v: Float) = (v * scaleW).dp
        fun refH(v: Float) = (v * scaleH).dp
        fun refSquare(v: Float) = (v * scaleAvg).dp
        fun refFont(v: Float) = (v * scaleW).sp

        @Composable
        fun Modifier.interactiveClick(onClick: () -> Unit): Modifier = this.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = LocalIndication.current,
            onClick = onClick
        )

        val infoY = refY(66f)
        val infoH = refH(90f)
        Box(
            modifier = Modifier
                .offset(x = refX(15f), y = infoY)
                .size(width = refW(330f), height = infoH)
                .background(InfoBlockBgColor)
        ) {
            Text(
                text = "UserLogin_123",
                color = ProfileTextColor,
                fontSize = refFont(24f),
                modifier = Modifier
                    .offset(x = refX(11f), y = refY(17f))
                    .widthIn(min = refW(107f), max = refW(280f))
            )
            Text(
                text = "Дата регистрации: 10.03.2026",
                color = ProfileTextColor,
                fontSize = refFont(16f),
                modifier = Modifier
                    .offset(x = refX(11f), y = refY(57f))
                    .widthIn(min = refW(225f), max = refW(280f))
            )
        }

        Box(
            modifier = Modifier
                .offset(x = refX(113f), y = refY(194f))
                .size(width = refW(130f), height = refH(45f))
                .background(ProfileButtonBgColor)
                .interactiveClick(onStartTest),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Пройти тест",
                color = ProfileButtonTextColor,
                fontSize = refFont(16f)
            )
        }

        Text(
            text = "История тестов",
            color = ProfileTextColor,
            fontSize = refFont(16f),
            modifier = Modifier
                .offset(x = refX(24f), y = refY(277f))
                .size(width = refW(117f), height = refH(19f))
        )

        Column(
            modifier = Modifier
                .offset(x = refX(15f), y = refY(330f))
                .width(refW(330f)),
            verticalArrangement = Arrangement.spacedBy(refH(10f))
        ) {
            listOf(1, 2).forEach { n ->
                Box(
                    modifier = Modifier
                        .width(refW(330f))
                        .height(refH(80f))
                        .background(TestBlockBgColor)
                ) {
                    Text(
                        text = "Тест $n",
                        color = ProfileTextColor,
                        fontSize = refFont(16f),
                        modifier = Modifier
                            .offset(x = refX(12f), y = refY(12f))
                            .widthIn(min = refW(47f))
                    )
                    Text(
                        text = "Результат: ${10 + n}/15",
                        color = ProfileTextColor,
                        fontSize = refFont(14f),
                        modifier = Modifier
                            .offset(x = refX(12f), y = refY(30f))
                            .widthIn(min = refW(110f))
                    )
                    Text(
                        text = "${15 + n}.03.2026",
                        color = ProfileTextColor,
                        fontSize = refFont(14f),
                        textAlign = TextAlign.End,
                        modifier = Modifier
                            .offset(x = refX(250f), y = refY(12f))
                            .widthIn(min = refW(60f))
                    )
                }
            }
        }

        BottomNavigationBar(
            activeScreen = activeScreen,
            onTabChange = onTabChange,
            refX = { refX(it) },
            refY = { refY(it) },
            refW = { refW(it) },
            refH = { refH(it) },
            refSquare = { refSquare(it) }
        )
    }
}