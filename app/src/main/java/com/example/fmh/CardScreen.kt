package com.example.fmh

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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

        @Composable
        fun Modifier.interactiveClick(onClick: () -> Unit): Modifier = this.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = LocalIndication.current,
            onClick = onClick
        )

        Text(
            text = "Источник: URL",
            color = CardSourceColor,
            fontSize = refFont(12f),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .offset(x = refX(139f), y = refY(586f))
                .widthIn(min = refW(82f))
        )

        // Скроллящаяся область (только картинка и шаги)
        val scrollTop = refY(90f)
        val scrollHeight = refY(580f) - scrollTop

        Column(
            modifier = Modifier
                .offset(y = scrollTop)
                .height(scrollHeight)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            // Картинка (Y=102 относительно экрана -> 12 относительно верха скролла)
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .offset(y = refY(12f))
                    .size(refSquare(250f))
                    .background(ListItemImageColor)
            )

            // Отступ до шагов: 375 - (102 + 250) = 23
            Spacer(modifier = Modifier.height(refH(23f)))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(refH(5f))
            ) {
                expandedSteps.forEachIndexed { i, isExp ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .then(if (isExp) Modifier.wrapContentHeight() else Modifier.height(refH(70f)))
                            .background(CardStepBgColor)
                            .interactiveClick { expandedSteps = expandedSteps.mapIndexed { idx, v -> if (idx == i) !v else v } }
                    ) {
                        // Фиксированная шапка блока
                        Box(modifier = Modifier.fillMaxWidth().height(refH(70f))) {
                            Text(
                                text = "Шаг ${i + 1}",
                                color = CardStepTextColor,
                                fontSize = refFont(20f),
                                modifier = Modifier.align(Alignment.CenterStart).offset(x = refX(25f)).widthIn(min = refW(55f))
                            )
                            Icon(
                                Icons.Default.ArrowDropDown,
                                null,
                                tint = CardStepArrowColor,
                                modifier = Modifier.align(Alignment.CenterEnd).offset(x = refX(-19f)).size(refSquare(20f)).rotate(if (isExp) 180f else 0f)
                            )
                        }
                        // Раскрывающаяся часть (высота автоматически подстраивается под текст)
                        if (isExp) {
                            Text(
                                text = "Подробная инструкция по выполнению данного этапа. Текст автоматически подстраивается под ширину блока, а высота контейнера увеличивается строго по объему содержимого, сохраняя заданный отступ до следующего элемента.",
                                color = CardStepTextColor,
                                fontSize = (18f * scaleW).coerceIn(14f, 20f).sp,
                                modifier = Modifier
                                    .padding(horizontal = refX(25f), vertical = refY(15f))
                                    .fillMaxWidth()
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(refY(50f)))
            }
        }

        // Фиксированные элементы (не скроллятся)
        Icon(
            Icons.AutoMirrored.Filled.ArrowBack,
            null,
            tint = CardBackIconColor,
            modifier = Modifier
                .offset(x = refX(18f), y = refY(42f))
                .size(refSquare(24f))
                .interactiveClick(onBack)
        )

        Box(
            modifier = Modifier
                .offset(x=refX(48f), y = refY(40f))
                .fillMaxWidth()
        ) {
            Text(
                text = title,
                color = CardTitleColor,
                fontSize = refFont(28f),
                modifier = Modifier.fillMaxWidth()
            )
        }

        Box(
            modifier = Modifier
                .offset(x = refX(260f), y = refY(530f))
                .size(refSquare(70f))
                .background(CardSpeakBtnColor, shape = CircleShape)
                .interactiveClick {},
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.AutoMirrored.Filled.VolumeUp, null, tint = CardSpeakIconColor, modifier = Modifier.size(refSquare(30f)))
        }

    }
}