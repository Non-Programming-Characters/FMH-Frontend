package ru.solomka.fmh.app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import ru.solomka.fmh.app.FirstAidItem
import ru.solomka.fmh.app.R

class FragmentDetail : Fragment() {

    companion object {
        const val ARG_ITEM = "arg_item"

        fun newInstance(item: FirstAidItem): FragmentDetail {
            val fragment = FragmentDetail()
            val args = Bundle()
            args.putParcelable(ARG_ITEM, item)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val item: FirstAidItem? = arguments?.getParcelable(ARG_ITEM)
        if (item == null) {
            requireActivity().onBackPressedDispatcher.onBackPressed()
            return
        }

        val btnBack: ImageView = view.findViewById(R.id.btnBack)
        val ivIcon: ImageView = view.findViewById(R.id.ivDetailIcon)
        val tvTitle: TextView = view.findViewById(R.id.tvDetailTitle)
        val tvShortDesc: TextView = view.findViewById(R.id.tvDetailShortDesc)
        val tvFullDesc: TextView = view.findViewById(R.id.tvDetailFullDesc)
        val tvDifficulty: TextView = view.findViewById(R.id.tvDetailDifficulty)
        val tvSource: TextView = view.findViewById(R.id.tvDetailSource)

        tvTitle.text = item.title
        tvShortDesc.text = item.shortDesc
        tvFullDesc.text = item.fullDesc
        ivIcon.setImageResource(item.iconResId)
        tvDifficulty.text = "Сложность: " + "★".repeat(item.difficulty) + "☆".repeat(3 - item.difficulty)
        tvSource.text = "Источник: ${item.source}"

        btnBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }
}