package com.example.asyncprogramming

import kotlinx.coroutines.Job

data class Stopwatch(
    var timeInMillis: Long = 0,
    var isRunning: Boolean = false,
    var job: Job? = null
)