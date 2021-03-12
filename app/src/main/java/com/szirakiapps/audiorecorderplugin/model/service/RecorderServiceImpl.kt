package com.szirakiapps.audiorecorderplugin.model.service

import android.net.Uri
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import com.szirakiapps.audiorecorderplugin.model.util.Recorder
import javax.inject.Inject

class RecorderServiceImpl @Inject constructor(
    private val recorder: Recorder
) : RecorderService {

    override val isRecording = MutableLiveData<Boolean>()

    override val currentVoiceAmplitude = MutableLiveData(0.0)

    override val elapsedMillis = MutableLiveData(0L)

    override fun isPauseResumeSupported(): Boolean = recorder.isPauseResumeSupported()

    override fun startRecording(uri: Uri) {
        recorder.start(uri)
        isRecording.value = true
        elapsedMillis.value = 0L
        loopAmplitude()
    }

    override fun resumeRecording(uri: Uri) {
        recorder.resume(uri)
        isRecording.value = true
        if (!recorder.isPauseResumeSupported()) {
            elapsedMillis.value = 0L
        }
        loopAmplitude()
    }

    override fun pauseRecording() {
        recorder.pause()
        isRecording.value = false
    }

    override fun stopRecording() {
        recorder.stop()
        isRecording.value = false
    }

    override fun cleanup() {
        stopRecording()
    }

    private fun loopAmplitude() {

        if (!recorder.isRecording() || isRecording.value == false) {
            return
        }

        currentVoiceAmplitude.value = recorder.getAmplitude()
        elapsedMillis.value = elapsedMillis.value?.plus(refreshTimeMillis)

        Looper.myLooper()?.let {
            Handler(it).postDelayed({
                loopAmplitude()
            }, refreshTimeMillis)
        }
    }

    companion object {
        const val refreshTimeMillis = 100L
    }
}