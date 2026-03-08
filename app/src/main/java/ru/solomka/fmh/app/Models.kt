package ru.solomka.fmh.app

import android.os.Parcel
import android.os.Parcelable
import com.example.fmh.R

data class FirstAidItem(
    val id: Int,
    val title: String,
    val shortDesc: String,
    val fullDesc: String,
    val iconResId: Int, // В реальном приложении это URL или Vector
    val difficulty: Int, // 1-3 звезды
    val source: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(shortDesc)
        parcel.writeString(fullDesc)
        parcel.writeInt(iconResId)
        parcel.writeInt(difficulty)
        parcel.writeString(source)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<FirstAidItem> {
        override fun createFromParcel(parcel: Parcel): FirstAidItem {
            return FirstAidItem(parcel)
        }

        override fun newArray(size: Int): Array<FirstAidItem?> {
            return arrayOfNulls(size)
        }
    }
}

object FirstAidRepository {
    // Генерация данных "на ходу"
    fun getAllItems(aidObjects: List<AidObject>): List<FirstAidItem> {



        return listOf(
            FirstAidItem(1, "Остановка кровотечения", "Пережмите артерию выше раны",
                "При артериальном кровотечении кровь алая и бьет фонтаном. Наложите жгут выше раны, подложите ткань.",
                R.drawable.ic_blood, 3, "Минздрав РФ"),
            FirstAidItem(2, "Ожог", "Промойте водой 15 минут",
                "Держите обожженное место под прохладной проточной водой. Не прикладывайте лед и не мажьте маслом.",
                R.drawable.ic_fire, 1, "ВОЗ"),
            FirstAidItem(3, "Перелом", "Обездвижьте конечность",
                "Наложите шину из подручных материалов. Зафиксируйте два сустава: выше и ниже перелома.",
                R.drawable.ic_bone, 2, "Красный Крест"),
            FirstAidItem(4, "Удушье", "Прием Геймлиха",
                "Встаньте сзади, обхватите руками, сожмите кулак над пупком и резко надавите вверх.",
                R.drawable.ic_lungs, 3, "AHA"),
            FirstAidItem(5, "Порез", "Промыть и заклеить",
                "Промойте антисептиком. Если края расходятся, используйте пластырь-бабочку.",
                R.drawable.ic_cut, 1, "Минздрав РФ")
        )
    }

    fun searchItems(query: String, allItems: List<FirstAidItem>): List<FirstAidItem> {
        // Если запрос пустой — возвращаем всё
        if (query.isBlank()) return allItems

        // Разбиваем запрос на слова (по пробелам), убираем пустые
        val searchWords = query.trim().split("\\s+".toRegex()).filter { it.isNotBlank() }
        if (searchWords.isEmpty()) return allItems

        return allItems.map { item ->
            val titleLower = item.title.lowercase()
            val shortDescLower = item.shortDesc.lowercase()
            val fullDescLower = item.fullDesc.lowercase()

            // Считаем "очки релевантности" для каждой карточки
            var score = 0

            for (word in searchWords) {
                val wordLower = word.lowercase()

                // 🔹 Проверяем вхождение слова в каждое поле
                // Заголовок: наибольший вес (3 балла)
                if (titleLower.contains(wordLower)) {
                    score += 3
                }
                // Краткое описание: средний вес (2 балла)
                if (shortDescLower.contains(wordLower)) {
                    score += 2
                }
                // Полное описание: минимальный вес (1 балл)
                if (fullDescLower.contains(wordLower)) {
                    score += 1
                }
            }

            // Возвращаем пару: карточка + её рейтинг
            item to score
        }
            // 🔹 Убираем ВСЕ карточки, где нет ни одного совпадения (score == 0)
            .filter { it.second > 0 }
            // Сортируем: от самых релевантных к наименее
            .sortedByDescending { it.second }
            // Оставляем только карточки (убираем рейтинг из результата)
            .map { it.first }
    }
}