package com.example.workmanagerdemo.data

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class DatabaseWriteWorker(context: Context, workerParams: WorkerParameters) : Worker(context,
    workerParams
) {
    override fun doWork(): Result {
        Thread.sleep(1000)
        println("${this::class.java.name}")

        val success = inputData.getBoolean("SUCCESS", false)
        val name = inputData.getString("NAME")
        return if (success) {
            name?.let { println("$name success") }
            println("Stored recommendation")
            Result.success()
        } else {
            name?.let { println("$name failure") }
            Result.failure()
        }
    }
}