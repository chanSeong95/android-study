package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import com.example.myapplication.model.Item

class ItemViewModel: ViewModel() {
    private val datas: MutableList<Item> = mutableListOf()

    fun get(): List<Item> {
        return datas
    }

    fun add(item: Item) {
        datas.add(item)
    }

    fun remove(index: Int) {
        datas.removeAt(index)
    }

    fun filter(predicate: (Item) -> Boolean ): List<Item> {
        return datas.filter { predicate(it) }
    }
}