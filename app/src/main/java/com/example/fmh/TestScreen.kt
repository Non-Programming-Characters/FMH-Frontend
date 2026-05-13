package com.example.fmh

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Question(val number: Int, val text: String, val options: List<String>, val correctIndex: Int)

@Composable
fun TestScreen(onBack: () -> Unit, onFinish: (Int) -> Unit) {
    val questions = remember { listOf(
        Question(1, "Вопрос 1: Пример текста?", listOf("Вариант ответа 1", "Вариант ответа 2", "Вариант ответа 3", "Вариант ответа 4"), 0),
        Question(2, "Вопрос 2: Пример текста?", listOf("Вариант ответа 1", "Вариант ответа 2", "Вариант ответа 3", "Вариант ответа 4"), 1)
    ) }

    var currentQIndex by remember { mutableIntStateOf(0) }
    var selectedOption by remember { mutableIntStateOf(-1) }
    var score by remember { mutableIntStateOf(0) }

    val currentQ = questions[currentQIndex]
    val isLast = currentQIndex == questions.lastIndex
    val isOptionSelected = selectedOption != -1
    val buttonColor = if (!isOptionSelected) TestButtonInactive else if (isLast) TestButtonActiveFinish else TestButtonActiveNext
    val buttonText = if (isLast) "Завершить тест" else "Следующий вопрос"

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

        Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = TestTextColor, modifier = Modifier.offset(x = refX(24f), y = refY(43f)).size(refSquare(24f)).interactiveClick(onBack))
        Text("В меню", color = TestTextColor, fontSize = refFont(20f), modifier = Modifier.offset(x = refX(61f), y = refY(44f)).size(width = refW(71f), height = refH(23f)))

        Box(modifier = Modifier.offset(x = refX(15f), y = refY(100f)).size(width = refW(335f), height = refH(80f)).background(TestBoxBgColor, shape = RoundedCornerShape(12.dp))) {
            Text("${currentQ.number}. ${currentQ.text}", color = TestTextColor, fontSize = refFont(24f), modifier = Modifier.offset(x = refX(20f), y = refY(36f)).size(width = refW(119f), height = refH(28f)))
        }

        Column(modifier = Modifier.offset(x = refX(15f), y = refY(227f)).fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(refH(10f))) {
            currentQ.options.forEachIndexed { idx, optionText ->
                val isSelected = selectedOption == idx
                Box(modifier = Modifier.fillMaxWidth().height(refH(44f)).background(TestOptionBgColor, shape = RoundedCornerShape(8.dp))
                    .border(if (isSelected) refSquare(2f) else refSquare(1f), if (isSelected) TestOptionSelectedBorder else Color.Transparent, RoundedCornerShape(8.dp))
                    .interactiveClick { selectedOption = idx }) {
                    Box(modifier = Modifier.offset(x = refX(21f), y = refY(23f)).size(refSquare(20f)).background(if (isSelected) TestOptionSelectedBorder else TestCheckboxBg, shape = RoundedCornerShape(4.dp))
                        .border(refSquare(1f), TestCheckboxBorder, RoundedCornerShape(4.dp)), contentAlignment = Alignment.Center) {
                        if (isSelected) Icon(Icons.Default.Check, null, tint = Color.White, modifier = Modifier.size(refSquare(14f)))
                    }
                    Text(optionText, color = TestTextColor, fontSize = refFont(16f), modifier = Modifier.offset(x = refX(62f), y = refY(23f)).size(width = refW(130f), height = refH(19f)))
                }
            }
        }

        Box(
            modifier = Modifier.offset(x = refX(80f), y = refY(454f)).size(width = refW(205f), height = refH(50f))
                .background(buttonColor, shape = RoundedCornerShape(10.dp))
                .then(if (isOptionSelected) Modifier.interactiveClick {
                    if (selectedOption == currentQ.correctIndex) score++
                    if (isLast) onFinish(score) else { selectedOption = -1; currentQIndex++ }
                } else Modifier),
            contentAlignment = Alignment.Center
        ) {
            Text(buttonText, color = TestButtonText, fontSize = refFont(16f))
        }
    }
}