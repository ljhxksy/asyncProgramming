package com.example.asyncprogramming
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*

class StopwatchAdapter(
    private val stopwatches: List<Stopwatch>,
    private val coroutineScope: CoroutineScope
) : RecyclerView.Adapter<StopwatchAdapter.StopwatchViewHolder>() {

    inner class StopwatchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val timeTextView: TextView = view.findViewById(R.id.timeTextView)
        val startButton: Button = view.findViewById(R.id.startButton)
        val resetButton: Button = view.findViewById(R.id.resetButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StopwatchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_stopwatch, parent, false)
        return StopwatchViewHolder(view)
    }

    override fun getItemCount(): Int {
        return stopwatches.size
    }

    override fun onBindViewHolder(holder: StopwatchViewHolder, position: Int) {
        val stopwatch = stopwatches[position]
        holder.timeTextView.text = formatTime(stopwatch.timeInMillis)

        holder.startButton.setOnClickListener {
            if (stopwatch.isRunning) {
                pauseStopwatch(stopwatch, holder)
            } else {
                startStopwatch(stopwatch, holder)
            }
        }
        holder.resetButton.setOnClickListener { resetStopwatch(stopwatch, holder) }
    }

    private fun formatTime(timeInMillis: Long): String {
        val milliseconds = (timeInMillis % 1000) / 10
        val seconds = (timeInMillis / 1000) % 60
        val minutes = (timeInMillis / (1000 * 60)) % 60
        return String.format("%02d:%02d.%02d", minutes, seconds, milliseconds)
    }

    private fun startStopwatch(stopwatch: Stopwatch, holder: StopwatchViewHolder) {
        stopwatch.isRunning = true
        holder.startButton.text = "Pause"

        stopwatch.job = coroutineScope.launch {
            while (stopwatch.isRunning) {
                delay(10)
                stopwatch.timeInMillis += 10
                holder.timeTextView.text = formatTime(stopwatch.timeInMillis)
            }
        }
    }

    private fun pauseStopwatch(stopwatch: Stopwatch, holder: StopwatchViewHolder) {
        stopwatch.isRunning = false
        stopwatch.job?.cancel()
        holder.startButton.text = "Resume"
    }

    private fun resetStopwatch(stopwatch: Stopwatch, holder: StopwatchViewHolder) {
        pauseStopwatch(stopwatch, holder)
        stopwatch.timeInMillis = 0
        holder.timeTextView.text = formatTime(stopwatch.timeInMillis)
        holder.startButton.text = "Start"
    }
}
