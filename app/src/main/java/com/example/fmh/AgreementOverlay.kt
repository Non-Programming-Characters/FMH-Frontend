package com.example.fmh

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp

@Composable
fun AgreementOverlay(
    onAgree: () -> Unit,
    scaleW: Float,
    scaleH: Float,
    scaleAvg: Float
) {
    var isCheckboxChecked by remember { mutableStateOf(false) }
    var expandedBlocks by remember { mutableStateOf(listOf(false, false)) }

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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ScrimColor)
            .pointerInput(Unit) {} // Блокирует все касания, не пропуская их на главный экран
    ) {
        Box(
            modifier = Modifier
                .offset(y = refY(50f))
                .size(width = refW(360f), height = refH(515f))
                .background(AgreementMainBg)
        ) {
            Text(
                text = "Пользовательское соглашение",
                color = AgreementTitleText,
                fontSize = refFont(16f),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(y = refY(16f))
                    .size(width = refW(236f), height = refH(19f))
            )

            Column(
                modifier = Modifier
                    .offset(y = refY(48f))
                    .fillMaxWidth()
            ) {
                listOf(0, 1).forEach { index ->
                    AgreementBlock(
                        index = index + 1,
                        isExpanded = expandedBlocks[index],
                        onToggle = {
                            expandedBlocks = expandedBlocks.mapIndexed { i, v -> if (i == index) !v else v }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        scaleW = scaleW,
                        scaleH = scaleH,
                        scaleAvg = scaleAvg
                    )
                    if (index < 1) Spacer(modifier = Modifier.height(refH(10f)))
                }
            }

            // Кнопка согласия с условиями [(45, 404), (270, 44)]
            Box(
                modifier = Modifier
                    .offset(x = refX(45f), y = refY(404f))
                    .size(width = refW(270f), height = refH(44f))
                    .background(AgreementCheckboxBg)
                    .border(refSquare(1f), AgreementCheckboxBorder)
                    .interactiveClick { isCheckboxChecked = !isCheckboxChecked },
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = "Я согласен(-а) с условиями",
                    color = AgreementCheckboxText,
                    fontSize = refFont(14f),
                    modifier = Modifier.offset(x = refX(22f)).widthIn(min = refW(180f))
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = refX(24f))
                        .size(refSquare(20f))
                        .background(if (isCheckboxChecked) AgreementCheckboxBorder else AgreementCheckmarkInner)
                        .border(refSquare(1f), AgreementCheckmarkBorder)
                        .interactiveClick { isCheckboxChecked = !isCheckboxChecked },
                    contentAlignment = Alignment.Center
                ) {
                    if (isCheckboxChecked) {
                        Icon(Icons.Default.Check, null, tint = AgreementCheckmarkInner, modifier = Modifier.size(refSquare(14f)))
                    }
                }
            }

            // Кнопка "Далее >" (нижняя граница совпадает с низом блока-фона)
            val isActive = isCheckboxChecked
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .size(width = refW(360f), height = refH(51f))
                    .background(if (isActive) AgreementButtonActive else AgreementButtonInactive)
                    .then(if (isActive) Modifier.interactiveClick(onAgree) else Modifier),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    text = "Далее >",
                    color = if (isActive) AgreementButtonTextActive else AgreementButtonTextInactive,
                    fontSize = refFont(16f),
                    modifier = Modifier
                        .padding(end = refX(30f))
                        .size(width = refW(60f), height = refH(19f))
                )
            }
        }
    }
}

@Composable
private fun AgreementBlock(
    index: Int,
    isExpanded: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier,
    scaleW: Float,
    scaleH: Float,
    scaleAvg: Float
) {
    fun refX(v: Float) = (v * scaleW).dp
    fun refY(v: Float) = (v * scaleH).dp
    fun refW(v: Float) = (v * scaleW).dp
    fun refH(v: Float) = (v * scaleH).dp
    fun refSquare(v: Float) = (v * scaleAvg).dp
    fun refFont(v: Float) = (v * scaleW).sp

    val headerH = refH(80f)

    Column(
        modifier = modifier
            .then(if (isExpanded) Modifier.wrapContentHeight() else Modifier.height(headerH))
            .background(AgreementBlockBg)
            .clickable(onClick = onToggle)
    ) {
        // Фиксированная шапка блока (всегда 80dp)
        Box(modifier = Modifier.fillMaxWidth().height(headerH)) {
            Text(
                text = "Блок $index",
                color = AgreementBlockText,
                fontSize = refFont(20f),
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .offset(x = refX(39f))
                    .widthIn(min = refW(63f))
            )
            Icon(
                Icons.Default.ArrowDropDown,
                null,
                tint = AgreementArrowColor,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .offset(x = refX(-39f))
                    .size(refSquare(20f))
                    .rotate(if (isExpanded) 180f else 0f)
            )
        }
        // Раскрывающаяся часть (высота автоматически подстраивается под объём текста)
        if (isExpanded) {
            Text(
                text = "Содержимое блока $index. Текст начинается ровно от нижней границы основного прямоугольника. Фон совпадает с родителем, высота подстраивается автоматически. Размер шрифта на 2 единицы меньше заголовка.",
                color = AgreementBlockText,
                fontSize = refFont(18f), // 20 - 2
                modifier = Modifier
                    .padding(horizontal = refX(20f), vertical = refY(15f))
                    .fillMaxWidth()
            )
        }
    }
}