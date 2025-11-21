package com.example.carkharidlo.utils

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

object ImageUtils {

    fun saveImageToInternalStorage(context: Context, imageUri: Uri): String {
        val inputStream: InputStream? = context.contentResolver.openInputStream(imageUri)
        val file = File(context.filesDir, "profile_image.jpg")

        val outputStream = FileOutputStream(file)
        inputStream?.copyTo(outputStream)

        inputStream?.close()
        outputStream.close()

        return file.absolutePath   // ← Saved permanently
    }
}
