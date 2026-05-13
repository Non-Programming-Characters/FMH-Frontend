package com.example.fmh

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun AgreementScreen(onAgree: () -> Unit) {
    var isCheckboxChecked by remember { mutableStateOf(false) }
    var expandedBlocks by remember { mutableStateOf(listOf(false, false)) }

    Dialog(
        onDismissRequest = { /* Нельзя закрыть без согласия */ },
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        Box(
            modifier = Modifier.fillMaxSize().background(DialogScrimColor),
            contentAlignment = Alignment.Center
        ) {
            BoxWithConstraints(modifier = Modifier.fillMaxWidth().heightIn(max = 640.dp)) {
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

                Box(
                    modifier = Modifier.offset(y = refY(50f)).size(width = refW(360f), height = refH(515f))
                        .background(ScreenBgColor, shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                ) {
                    Text("Пользовательское соглашение", color = AgreementTitleText, fontSize = refFont(16f), textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.TopCenter).offset(y = refY(16f)).size(width = refW(236f), height = refH(19f)))

                    listOf(0, 1).forEach { index ->
                        val blockY = refY(48f + index * (80f + 50f))
                        ExpandableAgreementBlock(index = index + 1, isExpanded = expandedBlocks[index], onToggle = {
                            expandedBlocks = expandedBlocks.mapIndexed { i, v -> if (i == index) !v else v }
                        }, modifier = Modifier.offset(y = blockY).size(width = refW(360f), height = refH(80f)), scaleW, scaleH, scaleAvg)
                    }

                    Box(
                        modifier = Modifier.offset(y = refY(404f)).size(width = refW(270f), height = refH(44f))
                            .background(AgreementCheckboxBg, shape = RoundedCornerShape(8.dp))
                            .border(refSquare(1f), AgreementCheckboxBorder, RoundedCornerShape(8.dp))
                            .interactiveClick { isCheckboxChecked = !isCheckboxChecked }
                    ) {
                        Text("Я согласен(-а) с условиями", color = AgreementCheckboxText, fontSize = refFont(14f),
                            modifier = Modifier.offset(x = refX(22f), y = refY(24f)).size(width = refW(180f), height = refH(16f)))
                        Box(
                            modifier = Modifier.offset(x = refX(226f), y = refY(22f)).size(refSquare(20f))
                                .background(if (isCheckboxChecked) AgreementCheckboxBorder else AgreementCheckmarkInner, shape = RoundedCornerShape(4.dp))
                                .border(refSquare(1f), AgreementCheckmarkBorder, RoundedCornerShape(4.dp))
                                .interactiveClick { isCheckboxChecked = !isCheckboxChecked },
                            contentAlignment = Alignment.Center
                        ) {
                            if (isCheckboxChecked) Icon(Icons.Default.Check, null, tint = AgreementCheckmarkInner, modifier = Modifier.size(refSquare(14f)))
                        }
                    }

                    val isButtonActive = isCheckboxChecked
                    Box(
                        modifier = Modifier.offset(y = refY(468f)).size(width = refW(360f), height = refH(51f))
                            .background(if (isButtonActive) AgreementButtonActive else AgreementButtonInactive, shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                            .then(if (isButtonActive) Modifier.interactiveClick(onAgree) else Modifier),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Text("Далее >", color = if (isButtonActive) AgreementButtonTextActive else AgreementButtonTextInactive, fontSize = refFont(16f),
                            modifier = Modifier.offset(x = refX(291f), y = refY(17f)).size(width = refW(60f), height = refH(19f)))
                    }
                }
            }
        }
    }
}

@Composable
private fun ExpandableAgreementBlock(index: Int, isExpanded: Boolean, onToggle: () -> Unit, modifier: Modifier, scaleW: Float, scaleH: Float, scaleAvg: Float) {
    fun refX(v: Float) = (v * scaleW).dp
    fun refY(v: Float) = (v * scaleH).dp
    fun refW(v: Float) = (v * scaleW).dp
    fun refH(v: Float) = (v * scaleH).dp
    fun refSquare(v: Float) = (v * scaleAvg).dp
    fun refFont(v: Float) = (v * scaleAvg).sp

    Box(modifier = modifier.background(AgreementBlockBg, shape = RoundedCornerShape(8.dp)).clickable(onClick = onToggle)) {
        Text("Блок $index", color = AgreementBlockText, fontSize = refFont(20f),
            modifier = Modifier.offset(x = refX(39f), y = refY(39f)).size(width = refW(63f), height = refH(23f)))
        Icon(Icons.Default.ArrowDropDown, null, tint = AgreementArrowColor,
            modifier = Modifier.offset(x = refX(321f), y = refY(30f)).size(refSquare(20f)).rotate(if (isExpanded) 180f else 0f))
        if (isExpanded) Text("Содержимое блока $index.", color = AgreementBlockText, fontSize = refFont(12f),
            modifier = Modifier.offset(y = refY(70f)).padding(horizontal = refX(20f)).fillMaxWidth())
    }
}