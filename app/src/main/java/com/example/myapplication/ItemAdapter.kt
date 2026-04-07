package com.example.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemTodoBinding
import com.example.myapplication.model.Item

class ItemAdapter(private val datas: MutableList<Item> = mutableListOf(), private val onDeleteClick: (Int) -> Unit) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
    private val visibleDatas = mutableListOf<Item>()
    private var currentFilter = Filter.ALL

    init {
        applyFilter(Filter.ALL)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding, onDeleteClick)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(visibleDatas[position])
    }

    override fun getItemCount(): Int = visibleDatas.size

    fun addItem(text: String) {
        datas.add(Item(text, false))
        applyFilter(currentFilter)
    }

    fun removeItem(index: Int) {
        datas.removeAt(index)
        applyFilter(currentFilter)
    }

    fun applyFilter(filter: Filter) {
        currentFilter = filter
        visibleDatas.clear()
˚
        val filtered = when (filter) {
            Filter.ALL -> datas
            Filter.ACTIVE -> datas.filter { !it.isDone }
            Filter.DONE -> datas.filter { it.isDone }
        }

        visibleDatas.addAll(filtered)
        notifyDataSetChanged()
    }

    // 일반적으로 Nested Class 형태로 많이 구현함
    inner class ItemViewHolder(private val binding: ItemTodoBinding, private val onDeleteClick: (Int) -> Unit) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            with(binding) {
                cbDone.setOnCheckedChangeListener(null)

                tvTask.text = item.text
                cbDone.isChecked = item.isDone

                cbDone.setOnCheckedChangeListener { _, isChecked ->
                    item.isDone = isChecked
                    applyFilter(currentFilter)
                }

                btnDelete.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        onDeleteClick(position)
                    }
                }
            }
        }
    }
}