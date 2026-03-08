package ru.solomka.fmh.app

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.fmh.R

class FirstAidAdapter(
    private val onItemClick: (FirstAidItem) -> Unit
) : ListAdapter<FirstAidItem, FirstAidAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_first_aid, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClick)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val icon: ImageView = itemView.findViewById(R.id.ivIcon)
        private val title: TextView = itemView.findViewById(R.id.tvTitle)
        private val desc: TextView = itemView.findViewById(R.id.tvShortDesc)
        private val difficulty: TextView = itemView.findViewById(R.id.tvDifficulty)

        @SuppressLint("SetTextI18n")
        fun bind(item: FirstAidItem, clickListener: (FirstAidItem) -> Unit) {
            title.text = item.title
            desc.text = item.shortDesc
            icon.setImageResource(item.iconResId)
            difficulty.text = "★".repeat(item.difficulty) + "☆".repeat(3 - item.difficulty)

            itemView.setOnClickListener { clickListener(item) }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<FirstAidItem>() {
        override fun areItemsTheSame(oldItem: FirstAidItem, newItem: FirstAidItem) =
            oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: FirstAidItem, newItem: FirstAidItem) =
            oldItem == newItem
    }
}