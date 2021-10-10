package com.example.workmanagerdemo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkContinuation
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.workmanagerdemo.R
import com.example.workmanagerdemo.data.NetworkRequestWorker
import com.example.workmanagerdemo.data.ObjectDetectionWorker
import com.example.workmanagerdemo.data.DatabaseWriteWorker
import com.example.workmanagerdemo.databinding.FragmentThirdBinding

class ThirdFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentThirdBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentThirdBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSingleChainSucceed.setOnClickListener(this)
        binding.btnSingleChainFail.setOnClickListener(this)
        binding.btnMultipleChainSucceed.setOnClickListener(this)
        binding.btnMultipleChainFail.setOnClickListener(this)
        binding.btnGroupChainSucceed.setOnClickListener(this)
        binding.btnGroupChainFail.setOnClickListener(this)
        binding.btnBack.setOnClickListener(this)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

    override fun onClick(p0: View?) {
        val context = requireContext()
        val workManager = WorkManager.getInstance(context)
        when (p0) {
            binding.btnBack -> {
                findNavController().navigate(R.id.action_thirdFragment_to_SecondFragment)
            }
            binding.btnSingleChainSucceed -> {
                val objectDetectionWorkRequest = OneTimeWorkRequestBuilder<ObjectDetectionWorker>()
                    .setInputData(workDataOf("SUCCESS" to true))
                    .build()
                val networkRequestWorkRequest = OneTimeWorkRequestBuilder<NetworkRequestWorker>()
                    .setInputData(workDataOf("SUCCESS" to true))
                    .build()
                val databaseWriteWorkRequest = OneTimeWorkRequestBuilder<DatabaseWriteWorker>()
                    .setInputData(workDataOf("SUCCESS" to true))
                    .build()

                workManager.beginWith(objectDetectionWorkRequest)
                    .then(networkRequestWorkRequest)
                    .then(databaseWriteWorkRequest)
                    .enqueue()
            }
            binding.btnSingleChainFail -> {
                val objectDetectionWorkRequest = OneTimeWorkRequestBuilder<ObjectDetectionWorker>()
                    .setInputData(workDataOf("SUCCESS" to true))
                    .build()
                val networkRequestWorkRequest = OneTimeWorkRequestBuilder<NetworkRequestWorker>()
                    .setInputData(workDataOf("SUCCESS" to false))
                    .build()
                val databaseWriteWorkRequest = OneTimeWorkRequestBuilder<DatabaseWriteWorker>()
                    .setInputData(workDataOf("SUCCESS" to true))
                    .build()

                workManager.beginWith(objectDetectionWorkRequest)
                    .then(networkRequestWorkRequest)
                    .then(databaseWriteWorkRequest)
                    .enqueue()
            }

            binding.btnGroupChainSucceed -> {
                val objectDetectionWorkRequest1 =
                    OneTimeWorkRequestBuilder<ObjectDetectionWorker>()
                        .setInputData(workDataOf("SUCCESS" to true))
                        .build()
                val objectDetectionWorkRequest2 =
                    OneTimeWorkRequestBuilder<ObjectDetectionWorker>()
                        .setInputData(workDataOf("SUCCESS" to true))
                        .build()
                val networkRequestWorkRequest =
                    OneTimeWorkRequestBuilder<NetworkRequestWorker>()
                        .setInputData(workDataOf("SUCCESS" to true))
                        .build()
                val databaseWriteWorkRequest = OneTimeWorkRequestBuilder<DatabaseWriteWorker>()
                    .setInputData(workDataOf("SUCCESS" to true))
                    .build()

                workManager.beginWith(
                    listOf(
                        objectDetectionWorkRequest1,
                        objectDetectionWorkRequest2
                    )
                )
                    .then(networkRequestWorkRequest)
                    .then(databaseWriteWorkRequest)
                    .enqueue()
            }
            binding.btnGroupChainFail -> {
                val objectDetectionWorkRequest1 = OneTimeWorkRequestBuilder<ObjectDetectionWorker>()
                    .setInputData(workDataOf("SUCCESS" to true))
                    .build()
                val objectDetectionWorkRequest2 = OneTimeWorkRequestBuilder<ObjectDetectionWorker>()
                    .setInputData(workDataOf("SUCCESS" to false))
                    .build()
                val networkRequestWorkRequest = OneTimeWorkRequestBuilder<NetworkRequestWorker>()
                    .setInputData(workDataOf("SUCCESS" to true))
                    .build()
                val databaseWriteWorkRequest = OneTimeWorkRequestBuilder<DatabaseWriteWorker>()
                    .setInputData(workDataOf("SUCCESS" to true))
                    .build()

                workManager.beginWith(
                    listOf(
                        objectDetectionWorkRequest1,
                        objectDetectionWorkRequest2
                    )
                )
                    .then(networkRequestWorkRequest)
                    .then(databaseWriteWorkRequest)
                    .enqueue()
            }
            binding.btnMultipleChainSucceed -> {
                val objectDetectionWorkRequest1 = OneTimeWorkRequestBuilder<ObjectDetectionWorker>()
                    .setInputData(workDataOf("SUCCESS" to true, "NAME" to "ONE"))
                    .build()
                val objectDetectionWorkRequest2 = OneTimeWorkRequestBuilder<ObjectDetectionWorker>()
                    .setInputData(workDataOf("SUCCESS" to true, "NAME" to "TWO"))
                    .build()
                val networkRequestWorkRequest1 = OneTimeWorkRequestBuilder<NetworkRequestWorker>()
                    .setInputData(workDataOf("SUCCESS" to true, "NAME" to "ONE"))
                    .build()
                val networkRequestWorkRequest2 = OneTimeWorkRequestBuilder<NetworkRequestWorker>()
                    .setInputData(workDataOf("SUCCESS" to true, "NAME" to "TWO"))
                    .build()
                val databaseWriteWorkRequest1 = OneTimeWorkRequestBuilder<DatabaseWriteWorker>()
                    .setInputData(workDataOf("SUCCESS" to true, "NAME" to "ONE"))
                    .build()
                val databaseWriteWorkRequest2 = OneTimeWorkRequestBuilder<DatabaseWriteWorker>()
                    .setInputData(workDataOf("SUCCESS" to true, "NAME" to "TWO"))
                    .build()

                val recommendation1 = workManager
                    .beginWith(objectDetectionWorkRequest1)
                    .then(networkRequestWorkRequest1)
                    .then(databaseWriteWorkRequest1)

                val recommendation2 = workManager
                    .beginWith(objectDetectionWorkRequest2)
                    .then(networkRequestWorkRequest2)
                    .then(databaseWriteWorkRequest2)

                val root = WorkContinuation
                    .combine(listOf(recommendation1, recommendation2))

                root.enqueue()
            }
            binding.btnMultipleChainFail -> {
                val objectDetectionWorkRequest1 = OneTimeWorkRequestBuilder<ObjectDetectionWorker>()
                    .setInputData(workDataOf("SUCCESS" to true, "NAME" to "ONE"))
                    .build()
                val objectDetectionWorkRequest2 = OneTimeWorkRequestBuilder<ObjectDetectionWorker>()
                    .setInputData(workDataOf("SUCCESS" to true, "NAME" to "TWO"))
                    .build()
                val networkRequestWorkRequest1 = OneTimeWorkRequestBuilder<NetworkRequestWorker>()
                    .setInputData(workDataOf("SUCCESS" to true, "NAME" to "ONE"))
                    .build()
                val networkRequestWorkRequest2 = OneTimeWorkRequestBuilder<NetworkRequestWorker>()
                    .setInputData(workDataOf("SUCCESS" to false, "NAME" to "TWO"))
                    .build()
                val databaseWriteWorkRequest1 = OneTimeWorkRequestBuilder<DatabaseWriteWorker>()
                    .setInputData(workDataOf("SUCCESS" to true, "NAME" to "ONE"))
                    .build()
                val databaseWriteWorkRequest2 = OneTimeWorkRequestBuilder<DatabaseWriteWorker>()
                    .setInputData(workDataOf("SUCCESS" to true, "NAME" to "TWO"))
                    .build()

                val recommendation1 = workManager.beginWith(objectDetectionWorkRequest1)
                    .then(networkRequestWorkRequest1)
                    .then(databaseWriteWorkRequest1)

                val recommendation2 = workManager.beginWith(objectDetectionWorkRequest2)
                    .then(networkRequestWorkRequest2)
                    .then(databaseWriteWorkRequest2)

                val root = WorkContinuation.combine(listOf(recommendation1, recommendation2))

                root.enqueue()
            }
        }
    }

}