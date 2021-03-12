package com.szirakiapps.audiorecorderplugin.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.szirakiapps.audiorecorderplugin.di.ContextComponent
import com.szirakiapps.audiorecorderplugin.model.service.RecorderService
import javax.inject.Inject

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val isRecording: MutableLiveData<Boolean>
        get() = recorderService.isRecording

    val currentVoiceAmplitude: MutableLiveData<Double>
        get() = recorderService.currentVoiceAmplitude

    val elapsedMillis: MutableLiveData<Long>
        get() = recorderService.elapsedMillis

    @Inject
    lateinit var recorderService: RecorderService

    init {
        ContextComponent.build(application).inject(this)
    }

    override fun onCleared() {
        super.onCleared()
        recorderService.cleanup()
    }

    fun isPauseResumeSupported(): Boolean = recorderService.isPauseResumeSupported()

    fun startRecording(uri: Uri) = recorderService.startRecording(uri)

    fun resumeRecording(uri: Uri) = recorderService.resumeRecording(uri)

    fun pauseRecording() = recorderService.pauseRecording()

    fun stopRecording() = recorderService.stopRecording()
}
