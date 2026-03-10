package ru.solomka.fmh.app.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import ru.solomka.fmh.app.MainActivity
import ru.solomka.fmh.app.R

class FragmentProfile : Fragment() {

    companion object {
        val cache = MainActivity.USER_DATA_CACHE
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.tvUserName).text = "Иван Иванов"
        view.findViewById<TextView>(R.id.tvRegDate).text = "Регистрация: 12.10.2023"

        view.findViewById<TextView>(R.id.tvLastTestTopic).text = "Тема: Сердечно-легочная реанимация"
        view.findViewById<TextView>(R.id.tvLastTestResult).text = "Результат: 85% (Отлично)"
        view.findViewById<TextView>(R.id.tvLastTestTime).text = "Время: 14:30, Вчера"
    }
}