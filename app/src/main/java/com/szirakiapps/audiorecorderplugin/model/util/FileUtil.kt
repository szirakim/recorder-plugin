package com.szirakiapps.audiorecorderplugin.model.util

import android.content.Context
import java.io.File
import javax.inject.Inject

class FileUtil @Inject constructor(
    context: Context,
){

    var directory: File = context.filesDir

    fun createNew(): File {
        val file = File(directory, "/rec-${System.currentTimeMillis()}.amr")
        file.createNewFile()
        return file
    }
}
