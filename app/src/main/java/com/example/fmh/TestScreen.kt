package com.example.fmh

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Question(
    val number: Int,
    val text: String,
    val options: List<String>,
    val correctIndex: Int
)

@Composable
fun TestScreen(
    onBack: () -> Unit,
    onFinish: (Int) -> Unit
) {
    val questions = remember {
        listOf(
            Question(1, "Вопрос 1: Пример длинного текста вопроса?", listOf("Вариант ответа 1", "Вариант ответа 2", "Вариант ответа 3", "Вариант ответа 4"), 0),
            Question(2, "Вопрос 2: Пример текста?", listOf("Вариант ответа 1", "Вариант ответа 2", "Вариант ответа 3", "Вариант ответа 4"), 1)
        )
    }
    var qIdx by remember { mutableIntStateOf(0) }
    var sel by remember { mutableIntStateOf(-1) }
    var score by remember { mutableIntStateOf(0) }
    val q = questions[qIdx]
    val isLast = qIdx == questions.lastIndex
    val btnColor = if (sel == -1) TestButtonInactive else if (isLast) TestButtonActiveFinish else TestButtonActiveNext
    val btnText = if (isLast) "Завершить тест" else "Следующий вопрос"

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

        Icon(
            Icons.AutoMirrored.Filled.ArrowBack,
            null,
            tint = TestTextColor,
            modifier = Modifier
                .offset(x = refX(24f), y = refY(43f))
                .size(refSquare(24f))
                .interactiveClick(onBack)
        )
        Text(
            text = "В меню",
            color = TestTextColor,
            fontSize = refFont(20f),
            modifier = Modifier
                .offset(x = refX(61f), y = refY(44f))
                .widthIn(min = refW(71f))
        )

        // Блок вопроса с переносом текста
        Box(
            modifier = Modifier
                .offset(x = refX(15f), y = refY(100f))
                .size(width = refW(335f), height = refH(80f))
                .heightIn(min = refH(80f))
                .background(TestBoxBgColor),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = "${q.number}. ${q.text}",
                color = TestTextColor,
                fontSize = refFont(24f),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .offset(x = refX(20f))
                    .widthIn(min = refW(119f), max = refW(290f))
                    .heightIn(min = refH(28f))
            )
        }

        Column(
            modifier = Modifier
                .offset(x = refX(15f), y = refY(227f))
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(refH(10f))
        ) {
            q.options.forEachIndexed { idx, opt ->
                val isSel = sel == idx
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(refH(44f))
                        .background(TestOptionBgColor)
                        .border(if (isSel) refSquare(2f) else refSquare(1f), if (isSel) TestOptionSelectedBorder else Color.Transparent)
                        .interactiveClick { sel = idx },
                    contentAlignment = Alignment.CenterStart
                ) {
                    Box(
                        modifier = Modifier
                            .offset(x = refX(21f))
                            .size(refSquare(20f))
                            .background(if (isSel) TestOptionSelectedBorder else TestCheckboxBg)
                            .border(refSquare(1f), TestCheckboxBorder),
                        contentAlignment = Alignment.Center
                    ) {
                        if (isSel) Icon(Icons.Default.Check, null, tint = Color.White, modifier = Modifier.size(refSquare(14f)))
                    }
                    Text(
                        text = opt,
                        color = TestTextColor,
                        fontSize = refFont(16f),
                        modifier = Modifier
                            .offset(x = refX(62f))
                            .widthIn(min = refW(130f))
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .offset(x = refX(80f), y = refY(454f))
                .size(width = refW(205f), height = refH(50f))
                .background(btnColor)
                .then(if (sel != -1) Modifier.interactiveClick {
                    if (sel == q.correctIndex) score++
                    if (isLast) onFinish(score) else { sel = -1; qIdx++ }
                } else Modifier),
            contentAlignment = Alignment.Center
        ) {
            Text(btnText, color = TestButtonText, fontSize = refFont(16f))
        }
    }
}