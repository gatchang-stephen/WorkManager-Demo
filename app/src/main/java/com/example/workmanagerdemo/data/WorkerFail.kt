package com.example.workmanagerdemo.data

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class WorkerFail(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {
        return Result.failure()
    }
}