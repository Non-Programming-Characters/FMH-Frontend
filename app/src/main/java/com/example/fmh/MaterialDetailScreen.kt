package com.example.fmh

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MaterialDetailScreen(
    title: String,
    onBack: () -> Unit
) {
    var desc by remember { mutableStateOf("") }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(ScreenBgColor)
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

        Text(
            text = "Полезные материалы и ссылки",
            color = HeaderTextColor,
            fontSize = refFont(20f),
            modifier = Modifier
                .offset(x = refX(24f), y = refY(44f))
                .size(width = refW(299f), height = refH(23f))
        )

        Box(
            modifier = Modifier
                .offset(y = refY(91f))
                .size(width = refW(360f), height = refH(495f))
                .background(ContainerBgColor),
            contentAlignment = Alignment.TopStart
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null,
                tint = ArrowIconColor,
                modifier = Modifier
                    .offset(x = refX(32f), y = refY(23f))
                    .size(refSquare(24f))
                    .interactiveClick(onBack)
            )
            Text(
                text = title,
                color = TestTextColor,
                fontSize = refFont(24f),
                maxLines = 2,
                modifier = Modifier
                    .offset(x = refX(114f), y = refY(19f))
                    .widthIn(min = refW(131f))
            )
            Box(
                modifier = Modifier
                    .offset(x = refX(44f), y = refY(71f))
                    .size(width = refW(286f), height = refH(364f))
                    .verticalScroll(rememberScrollState())
            ) {
                BasicTextField(
                    value = desc,
                    onValueChange = { desc = it },
                    modifier = Modifier.fillMaxSize(),
                    textStyle = TextStyle(
                        color = DescTextColor,
                        fontSize = refFont(20f)
                    ),
                    decorationBox = { inner ->
                        Box(
                            contentAlignment = Alignment.TopStart,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            if (desc.isEmpty()) {
                                Text(
                                    text = "Описание",
                                    color = DescPlaceholderColor,
                                    fontSize = refFont(20f)
                                )
                            }
                            inner()
                        }
                    }
                )
            }
            Text(
                text = "Ссылка: URL",
                color = LinkTextColor,
                fontSize = refFont(20f),
                modifier = Modifier
                    .offset(x = refX(44f), y = refY(445f))
                    .size(width = refW(119f), height = refH(23f))
            )
        }
    }
}