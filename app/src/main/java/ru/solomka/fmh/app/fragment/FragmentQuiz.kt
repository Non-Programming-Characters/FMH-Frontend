package ru.solomka.fmh.app.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.progressindicator.LinearProgressIndicator
import ru.solomka.fmh.app.R

class FragmentQuiz : Fragment() {

    private var currentQuestionIndex = 0
    private val questions = listOf(
        "Что делать при ожоге 2 степени?",
        "Как накладывать жгут?",
        "Можно ли давать воду при потере сознания?"
    )
    private val answers = listOf(
        arrayOf("Проткнуть пузырь", "Промыть водой", "Намазать маслом"),
        arrayOf("На голую кожу", "Через ткань", "На одежду"),
        arrayOf("Да, немного", "Нет, категорически", "Только чай")
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_quiz, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvQuestion = view.findViewById<TextView>(R.id.tvQuestion)
        val btnA = view.findViewById<Button>(R.id.btnOptionA)
        val btnB = view.findViewById<Button>(R.id.btnOptionB)
        val btnC = view.findViewById<Button>(R.id.btnOptionC)
        val progressBar = view.findViewById<LinearProgressIndicator>(R.id.progressBar)

        @SuppressLint("SetTextI18n")
        fun loadQuestion() {
            if (currentQuestionIndex >= questions.size) {
                tvQuestion.text = "Тест завершен!"
                btnA.visibility = View.GONE; btnB.visibility = View.GONE; btnC.visibility = View.GONE
                return
            }
            tvQuestion.text = "${currentQuestionIndex + 1}. ${questions[currentQuestionIndex]}"
            val opts = answers[currentQuestionIndex]
            btnA.text = opts[0]; btnB.text = opts[1]; btnC.text = opts[2]

            progressBar.progress = ((currentQuestionIndex + 1) * 100) / questions.size
        }

        fun nextQuestion() {
            currentQuestionIndex++
            loadQuestion()
        }

        listOf(btnA, btnB, btnC).forEach { btn ->
            btn.setOnClickListener { nextQuestion() }
        }

        loadQuestion()
    }
}