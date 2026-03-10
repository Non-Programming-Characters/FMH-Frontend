package ru.solomka.fmh.app.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.solomka.fmh.app.FirstAidAdapter
import ru.solomka.fmh.app.FirstAidRepository
import ru.solomka.fmh.app.FirstAidItem
import ru.solomka.fmh.app.R

class FragmentHome : Fragment() {

    private var _adapter: FirstAidAdapter? = null
    private val adapter get() = _adapter!!
    private var allItems: List<FirstAidItem> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        allItems = listOf() // todo: Request

        val etSearch: EditText = view.findViewById(R.id.etSearch)
        val btnClearSearch: ImageView = view.findViewById(R.id.btnClearSearch)
        val recyclerView: RecyclerView = view.findViewById(R.id.rvList)

        _adapter = FirstAidAdapter { item ->
            val detailFragment = FragmentDetail.newInstance(item)
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(
                    R.id.fragmentContainer,
                    detailFragment
                )
                .addToBackStack(null)
                .commit()
        }

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        adapter.submitList(allItems)

        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s?.toString() ?: ""

                btnClearSearch.visibility = if (query.isNotEmpty()) View.VISIBLE else View.GONE

                val filtered = if (query.isEmpty()) {
                    allItems
                } else {
                    FirstAidRepository.searchItems(query, allItems)
                }

                adapter.submitList(filtered)
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        btnClearSearch.setOnClickListener {
            etSearch.setText("")
            btnClearSearch.visibility = View.GONE
            adapter.submitList(allItems)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _adapter = null
    }
}