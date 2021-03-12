package com.szirakiapps.audiorecorderplugin.model.service

import android.net.Uri
import androidx.lifecycle.MutableLiveData

interface RecorderService {

    val isRecording: MutableLiveData<Boolean>

    val currentVoiceAmplitude: MutableLiveData<Double>

    val elapsedMillis: MutableLiveData<Long>

    fun isPauseResumeSupported(): Boolean

    fun startRecording(uri: Uri)

    fun resumeRecording(uri: Uri)

    fun pauseRecording()

    fun stopRecording()

    fun cleanup()
}