package com.example.workmanagerdemo.data

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class SimpleWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    override fun doWork(): Result {
        val message = inputData.getString("WORK_MESSAGE")
        Thread.sleep(10000)
        WorkSingletonStatus.workComplete = true
        if (message != null) {
            WorkSingletonStatus.workMessage = message
        }
        return Result.success()
    }
}