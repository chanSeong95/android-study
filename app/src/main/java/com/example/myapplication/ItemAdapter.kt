package com.example.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemTodoBinding
import com.example.myapplication.model.Item
import com.example.myapplication.viewmodel.ItemViewModel

class ItemAdapter(private val viewModel: ItemViewModel, private val onDeleteClick: (Int) -> Unit) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
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
        viewModel.add(Item(text, false))
        applyFilter(currentFilter)
    }

    fun removeItem(index: Int) {
        viewModel.remove(index)
        applyFilter(currentFilter)
    }

    fun applyFilter(filter: Filter) {
        currentFilter = filter
        visibleDatas.clear()

        val filtered = when (filter) {
            Filter.ALL -> viewModel.get()
            Filter.ACTIVE -> viewModel.filter { !it.isDone }
            Filter.DONE -> viewModel.filter { it.isDone }
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