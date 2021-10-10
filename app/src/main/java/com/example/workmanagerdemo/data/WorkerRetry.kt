package com.example.workmanagerdemo.data

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class WorkerRetry(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    override fun doWork(): Result {
        println("Work retry status: ${WorkSingletonStatus.workRetries}")
        return if (WorkSingletonStatus.workRetries < 3) {
            WorkSingletonStatus.workRetries += 1
            Result.retry()
        } else
            Result.success()
    }
}