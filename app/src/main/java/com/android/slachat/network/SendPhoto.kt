package com.android.slachat.network

import android.content.ContentResolver
import android.net.Uri
import clean.android.network.auth.ApiTokenProvider
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

class SendPhoto : KoinComponent {
    private val tokenProvider: ApiTokenProvider by inject()
    val client = OkHttpClient()

    fun uploadPhoto(contentResolver: ContentResolver, imageUri: Uri?) {
        if (imageUri == null) {
            return
        }

        // Use the ContentResolver to open the selected image URI and read its data
        try {
            val inputStream: InputStream? = contentResolver.openInputStream(imageUri)

            if (inputStream != null) {
                val imageFile =
                    InputStreamToFileConverter.convert(inputStream, contentResolver, imageUri)

                val requestBody = MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart(
                        "file",
                        imageFile.name,
                        RequestBody.create("image/*".toMediaTypeOrNull(), imageFile)
                    )
                    .addFormDataPart("userId", "3") // Removed the "chatId" field
                    .build()

                val request = Request.Builder()
                    .url("http://192.168.0.102:8080/photos/upload")
                    .post(requestBody)
                    .build()

                val response: Response = client.newCall(request).execute()
                // Handle the response as needed
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun getUserPhoto(userId: Long): ByteArray? {
        val request = Request.Builder()
            .url("http://192.168.0.102:8080/photos/user/$userId") // Replace with your server's URL
            .get()
            .build()

        try {
            val response: Response = client.newCall(request).execute()
            if (response.isSuccessful) {
                return response.body.bytes()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

}


object InputStreamToFileConverter {
    @Throws(IOException::class)
    fun convert(inputStream: InputStream?, contentResolver: ContentResolver, uri: Uri): File {
        val outputFile = createTempFile(contentResolver, uri)
        inputStream?.use { input ->
            FileOutputStream(outputFile).use { output ->
                input.copyTo(output)
            }
        }
        return outputFile
    }

    @Throws(IOException::class)
    private fun createTempFile(contentResolver: ContentResolver, uri: Uri): File {
        val extension = getFileExtension(contentResolver, uri)
        return File.createTempFile("temp_image", ".$extension")
    }

    private fun getFileExtension(contentResolver: ContentResolver, uri: Uri): String {
        val mime = contentResolver.getType(uri)
        return mime?.substringAfterLast('/')
            ?: "jpg" // Default to "jpg" if MIME type is not available
    }
}
