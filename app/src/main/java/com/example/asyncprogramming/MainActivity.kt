package com.example.asyncprogramming

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StopwatchAdapter
    private val stopwatches = mutableListOf<Stopwatch>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.stopwatchRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = StopwatchAdapter(stopwatches, this)
        recyclerView.adapter = adapter

        findViewById<Button>(R.id.addStopwatchButton).setOnClickListener {
            stopwatches.add(Stopwatch())
            adapter.notifyItemInserted(stopwatches.size - 1)
        }
    }
}
