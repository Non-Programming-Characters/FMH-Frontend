package com.example.fmh

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MaterialsScreen(activeScreen: String, onTabChange: (String) -> Unit, onMaterialClick: (Int) -> Unit) {
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

        Text("Полезные материалы и ссылки", color = MaterialsTitleColor, fontSize = refFont(20f), modifier = Modifier.offset(x = refX(24f), y = refY(44f)).size(width = refW(299f), height = refH(23f)))

        LazyColumn(modifier = Modifier.offset(y = refY(91f)).height(refY(545f) - refY(91f)).fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(refH(5f))) {
            items(8) { idx ->
                Box(modifier = Modifier.fillMaxWidth().height(refH(70f)).background(MaterialsItemBgColor, shape = RoundedCornerShape(8.dp)).interactiveClick { onMaterialClick(idx + 1) }) {
                    Text("Материал ${idx + 1}", color = MaterialsItemTextColor, fontSize = refFont(20f), modifier = Modifier.align(Alignment.TopStart).offset(x = refX(25f), y = refY(24f)).size(width = refW(110f), height = refH(23f)))
                    Icon(Icons.Default.ChevronRight, null, tint = MaterialsItemArrowColor, modifier = Modifier.align(Alignment.TopStart).offset(x = refX(321f), y = refY(25f)).size(refSquare(20f)))
                }
            }
        }

        BottomNavigationBar(activeScreen, onTabChange, { refX(it) }, { refY(it) }, { refW(it) }, { refH(it) }, { refSquare(it) })
    }
}