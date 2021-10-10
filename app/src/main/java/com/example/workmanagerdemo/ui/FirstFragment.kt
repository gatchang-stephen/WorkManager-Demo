package com.example.workmanagerdemo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.work.*
import com.example.workmanagerdemo.R
import com.example.workmanagerdemo.data.SimpleWorker
import com.example.workmanagerdemo.data.WorkSingletonStatus
import com.example.workmanagerdemo.databinding.FragmentFirstBinding
import java.util.concurrent.TimeUnit

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnStartWork.setOnClickListener(this)
        binding.btnWorkStatus.setOnClickListener(this)
        binding.btnResetStatus.setOnClickListener(this)
        binding.btnWorkUIThread.setOnClickListener(this)
        binding.btnNext.setOnClickListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(p0: View?) {
        when (p0) {
            binding.btnStartWork -> {
                val data = workDataOf("WORK_MESSAGE" to "Message Complete!")
                val constraints = Constraints.Builder()
                    .setRequiresCharging(true)
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
                val workRequest: WorkRequest = OneTimeWorkRequestBuilder<SimpleWorker>()
                    .setInputData(data)
                    .setConstraints(constraints)
                    .build()
                val periodicWorkRequest = PeriodicWorkRequestBuilder<SimpleWorker>(
                    5,
                    TimeUnit.MINUTES,
                    1,
                    TimeUnit.MINUTES
                )
                    .build()

                WorkManager.getInstance(this.requireContext())
                    .enqueue(workRequest)
            }
            binding.btnWorkStatus -> {
                Toast.makeText(
                    this.requireContext(),
                    "The work status is: ${WorkSingletonStatus.workMessage}",
                    Toast.LENGTH_LONG
                ).show()
            }
            binding.btnResetStatus -> {
                WorkSingletonStatus.workComplete = false
            }
            binding.btnWorkUIThread -> {
                Thread.sleep(10000)
                WorkSingletonStatus.workComplete = true
            }
            binding.btnNext -> {
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            }

        }
    }
}