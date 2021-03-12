package com.szirakiapps.audiorecorderplugin.view

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.szirakiapps.audiorecorderplugin.R
import com.szirakiapps.audiorecorderplugin.databinding.FragmentRecorderBinding
import com.szirakiapps.audiorecorderplugin.viewmodel.MainViewModel
import javax.inject.Inject

class RecorderFragment @Inject constructor(
    private val mainViewModel: MainViewModel,
) : Fragment() {

    private lateinit var binding: FragmentRecorderBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recorder, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = DataBindingUtil.bind(view) ?: return

        binding.btnRecordStart.setOnClickListener {
            binding.audioRecordView.recreate()
            requireActivity().intent.data?.let {
                mainViewModel.startRecording(it)
            }
        }

        binding.btnRecordPause.setOnClickListener {
            mainViewModel.pauseRecording()
        }

        binding.btnRecordResume.setOnClickListener {
            if (!mainViewModel.isPauseResumeSupported()) {
                binding.audioRecordView.recreate()
            }
            requireActivity().intent.data?.let {
                mainViewModel.resumeRecording(it)
            }
        }

        binding.btnSaveAndExit.setOnClickListener {
            mainViewModel.stopRecording()
            requireActivity().setResult(Activity.RESULT_OK)
            requireActivity().finish()
        }

        mainViewModel.isRecording.observe(viewLifecycleOwner) {
            binding.btnRecordStart.visibility = View.GONE
            binding.btnRecordResume.visibility = if (it) View.GONE else View.VISIBLE
            binding.btnRecordPause.visibility = if (it) View.VISIBLE else View.GONE
            binding.btnSaveAndExit.visibility = View.VISIBLE
        }

        mainViewModel.currentVoiceAmplitude.observe(viewLifecycleOwner) {
            binding.audioRecordView.update(it.toInt())
        }

        mainViewModel.elapsedMillis.observe(viewLifecycleOwner) {
            binding.txtRecordingTime.text = getElapsedTimeText(it)
        }
    }

    private fun getElapsedTimeText(timeMillis: Long): String {

        val elapsedSeconds = timeMillis / 1000

        val seconds = "0".plus((elapsedSeconds % 60).toString()).takeLast(2)
        val minutes = (elapsedSeconds / 60).toString()
            .let { m -> m.takeIf { it.length == 1 }?.let { "0$it" } ?: m }

        return "$minutes:$seconds"
    }
}