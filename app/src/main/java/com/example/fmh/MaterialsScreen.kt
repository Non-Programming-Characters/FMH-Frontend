package com.example.fmh

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MaterialsScreen(
    activeScreen: String,
    onTabChange: (String) -> Unit,
    onMaterialClick: (Int) -> Unit
) {
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize().background(ScreenBgColor)
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

        val dynamicTitleSize = (20f * scaleW).coerceIn(16f, 24f).sp

        @Composable
        fun Modifier.interactiveClick(onClick: () -> Unit): Modifier = this.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = LocalIndication.current,
            onClick = onClick
        )

        Text(
            text = "Полезные материалы и ссылки",
            color = MaterialsTitleColor,
            fontSize = dynamicTitleSize,
            modifier = Modifier
                .offset(x = refX(24f), y = refY(44f))
                .widthIn(min = refW(299f), max = refW(320f))
        )

        LazyColumn(
            modifier = Modifier
                .offset(y = refY(91f))
                .height(refY(545f) - refY(91f))
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(refH(5f))
        ) {
            items(8) { i ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(refH(70f))
                        .background(MaterialsItemBgColor)
                        .interactiveClick { onMaterialClick(i + 1) }
                ) {
                    Text(
                        text = "Материал ${i + 1}",
                        color = MaterialsItemTextColor,
                        fontSize = refFont(20f),
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .offset(x = refX(25f), y = refY(24f))
                            .widthIn(min = refW(110f))
                    )
                    Icon(
                        Icons.Default.ChevronRight,
                        null,
                        tint = MaterialsItemArrowColor,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .offset(x = refX(321f), y = refY(25f))
                            .size(refSquare(20f))
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