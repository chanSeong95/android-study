package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ItemAdapter
    private var itemCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            adapter = ItemAdapter { position ->
                adapter.removeItem(position)
            }

            rvTodo.layoutManager = LinearLayoutManager(this@MainActivity)
            rvTodo.adapter = adapter

            btnAdd.setOnClickListener {
                val text = etTask.text.toString()
                if (text.isNotBlank()) {
                    adapter.addItem(text)
                    etTask.text?.clear()
                }
            }

            chipAll.setOnClickListener {
                check(true)
                chipActive.isChecked = false
                chipDone.isChecked = false
                adapter.applyFilter(Filter.ALL)
            }

            chipActive.setOnClickListener {
                chipAll.isChecked = false
                check(true)
                chipDone.isChecked = false
                adapter.applyFilter(Filter.ACTIVE)
            }

            chipDone.setOnClickListener {
                chipAll.isChecked = false
                chipActive.isChecked = false
                check(true)
                adapter.applyFilter(Filter.DONE)
            }
        }
    }
}