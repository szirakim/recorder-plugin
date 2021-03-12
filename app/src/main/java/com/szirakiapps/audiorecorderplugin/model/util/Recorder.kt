package com.szirakiapps.audiorecorderplugin.model.util

import android.content.ContentResolver
import android.media.MediaRecorder
import android.net.Uri
import android.os.ParcelFileDescriptor
import java.io.File
import javax.inject.Inject

class Recorder @Inject constructor(
    private val contentResolver: ContentResolver,
) {

    private var fileDescriptor: ParcelFileDescriptor? = null

    private var recorder: MediaRecorder? = null

    fun start(uri: Uri) {
        stop()

        // contentResolver.openOutputStream(uri)
        fileDescriptor = contentResolver.openFileDescriptor(uri, "rw")
        start()
    }

    fun start(file: File) {
        stop()

        this.fileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_WRITE)
        start()
    }

    private fun start() {
        recorder = MediaRecorder()
        recorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
        recorder!!.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB)
        recorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

        fileDescriptor?.fileDescriptor?.let {
            recorder!!.setOutputFile(it)
            recorder!!.prepare()
            recorder!!.start()
        } ?: stop()
    }

    // below api 24, continuing recording is not possible directly
    fun isPauseResumeSupported(): Boolean = isPauseSupported()

    fun resume(uri: Uri) {
        if (isPauseResumeSupported()) {
            recorder?.resume()
        } else {
            start(uri)
        }
    }

    fun pause() {
        if (isPauseResumeSupported()) {
            recorder?.pause()
        } else {
            recorder?.stop()
        }
    }

    fun stop() {
        try {
            recorder?.stop()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            recorder?.release()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        recorder = null
        fileDescriptor = null
    }

    fun getAmplitude(): Double {
        return recorder?.maxAmplitude?.toDouble() ?: 0.0
    }

    fun isRecording(): Boolean = recorder != null

    fun invertRecording(uri: Uri) {
        if (isRecording()) {
            stop()
        } else {
            start(uri)
        }
    }

    companion object {
        @JvmStatic
        fun isPauseSupported(): Boolean {
            return android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N
        }
    }
}
