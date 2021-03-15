package com.pcr.drainit.utill

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import com.google.android.gms.maps.model.LatLng
import org.json.JSONObject
import java.io.BufferedInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream

object Util {

    fun geoToLatLong(json: String?): LatLng {
        val geometry = json?.let { JSONObject(it) }
        val coord = geometry?.getJSONArray("coordinates")

        return LatLng(coord?.getDouble(0) ?: 0.0, coord?.getDouble(1) ?: 0.0)
    }

    private fun getMimeTypeFromUri(uri: Uri?, context: Context): String {
        return if (uri == null) {
            ""
        } else {
            val cR: ContentResolver = context.contentResolver
            cR.getType(uri) ?: ""
        }
    }

    fun isImageFile(uri: Uri?, context: Context): Boolean {
        val fileType = getMimeTypeFromUri(uri, context)
        val fileExtension = uri.toString().substring(uri.toString().lastIndexOf(".") + 1)

        return fileType.contains("image", true) ||
                fileExtension.contains("jpeg", true) ||
                fileExtension.contains("jpg", true) ||
                fileExtension.contains("png", true)
    }

    fun uriToFile(uri: Uri?): File {
        return File(uri?.path ?: "")
    }

    fun encodeFileToBase64(file: File): String {
        val img = BitmapFactory.decodeFile(file.path)
        val byteArrayOutput = ByteArrayOutputStream()

        img.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutput)

        return Base64.encodeToString(byteArrayOutput.toByteArray(), Base64.DEFAULT)
    }

    fun encodeUriToFileToBase64(uri: Uri?): String {
        return encodeFileToBase64(uriToFile(uri))
    }

    fun encodeBitmaptoBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)

        return Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT)
    }

}