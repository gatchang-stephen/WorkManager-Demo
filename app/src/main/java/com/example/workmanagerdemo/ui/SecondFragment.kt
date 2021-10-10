package com.example.workmanagerdemo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.work.BackoffPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.workmanagerdemo.R
import com.example.workmanagerdemo.data.WorkerFail
import com.example.workmanagerdemo.data.WorkerRetry
import com.example.workmanagerdemo.databinding.FragmentSecondBinding
import java.util.concurrent.TimeUnit

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnWorkerFail.setOnClickListener(this)
        binding.btnWorkerRetry.setOnClickListener(this)
        binding.btnBack.setOnClickListener(this)
        binding.btnNext.setOnClickListener(this)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(p0: View?) {
        when (p0) {
            binding.btnWorkerFail -> {
                val workRequest = OneTimeWorkRequestBuilder<WorkerFail>()
                    .build()
                WorkManager.getInstance(this.requireContext())
                    .enqueue(workRequest)
            }
            binding.btnWorkerRetry -> {
                val workRequest = OneTimeWorkRequestBuilder<WorkerRetry>()
                    .setBackoffCriteria(
                        BackoffPolicy.LINEAR, 1, TimeUnit.SECONDS
                    ).build()
                WorkManager.getInstance(this.requireContext())
                    .enqueue(workRequest)
            }
            binding.btnBack -> {
                findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
            }
            binding.btnNext -> {
                findNavController().navigate(R.id.action_SecondFragment_to_thirdFragment)
            }
        }
    }
}