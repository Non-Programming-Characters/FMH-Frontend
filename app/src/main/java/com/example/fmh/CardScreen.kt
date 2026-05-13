package com.example.fmh

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CardScreen(title: String, onBack: () -> Unit) {
    var expandedSteps by remember { mutableStateOf(listOf(false, false, false, false)) }

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

        Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = CardBackIconColor, modifier = Modifier.offset(x = refX(18f), y = refY(42f)).size(refSquare(24f)).interactiveClick(onBack))
        Text(title, color = CardTitleColor, fontSize = refFont(28f), modifier = Modifier.offset(x = refX(90f), y = refY(40f)).size(width = refW(230f), height = refH(28f)).widthIn(min = refW(230f)))
        Box(modifier = Modifier.offset(x = refX(55f), y = refY(102f)).size(refSquare(250f)).background(ListItemImageColor, shape = RoundedCornerShape(12.dp)))

        LazyColumn(state = rememberLazyListState(), modifier = Modifier.offset(y = refY(375f)).fillMaxWidth().height(refH(160f)), verticalArrangement = Arrangement.spacedBy(refH(5f))) {
            items(expandedSteps.size) { i ->
                Box(modifier = Modifier.fillMaxWidth().height(refH(70f)).background(CardStepBgColor, shape = RoundedCornerShape(8.dp)).interactiveClick { expandedSteps = expandedSteps.mapIndexed { idx, v -> if (idx == i) !v else v } }) {
                    Text("Шаг ${i + 1}", color = CardStepTextColor, fontSize = refFont(20f), modifier = Modifier.align(Alignment.TopStart).offset(x = refX(25f), y = refY(24f)).size(width = refW(55f), height = refH(23f)))
                    Icon(Icons.Default.ArrowDropDown, null, tint = CardStepArrowColor, modifier = Modifier.align(Alignment.CenterEnd).padding(end = refX(19f)).size(refSquare(20f)).rotate(if (expandedSteps[i]) 180f else 0f))
                    if (expandedSteps[i]) Text("Описание шага ${i + 1}.", color = CardStepTextColor, fontSize = refFont(12f), modifier = Modifier.align(Alignment.TopStart).offset(x = refX(25f), y = refY(48f)).padding(end = refX(30f)))
                }
            }
        }

        Box(modifier = Modifier.offset(x = refX(260f), y = refY(530f)).size(refSquare(70f)).background(CardSpeakBtnColor, shape = CircleShape).interactiveClick { /* Озвучить */ }, contentAlignment = Alignment.Center) {
            Icon(Icons.AutoMirrored.Filled.VolumeUp, null, tint = CardSpeakIconColor, modifier = Modifier.size(refSquare(30f)))
        }

        Text("Источник: URL", color = CardSourceColor, fontSize = refFont(12f), textAlign = TextAlign.Center, modifier = Modifier.offset(x = refX(139f), y = refY(586f)).size(width = refW(82f), height = refH(14f)).widthIn(min = refW(82f)))
    }
}