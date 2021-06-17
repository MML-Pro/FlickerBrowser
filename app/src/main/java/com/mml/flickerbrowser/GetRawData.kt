package com.mml.flickerbrowser

import android.util.Log
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL

enum class DownloadStatus {
    OK, IDEL, NOT_INITILAISED, FAILD_OR_EMPTY, PERMISSION_ERROR, ERROR
}

private const val TAG = "GetRawData"
private var downloadStatus = DownloadStatus.IDEL


fun getRawData(url: String): String {

    try {
        downloadStatus = DownloadStatus.OK
        return URL(url).readText()
    } catch (ex: Exception) {
        val errorMessage = when (ex) {
            is MalformedURLException -> {
                downloadStatus = DownloadStatus.NOT_INITILAISED
                "doInBackground : Invalid URL ${ex.message}"
            }
            is IOException -> {
                downloadStatus = DownloadStatus.FAILD_OR_EMPTY
                "doInBackground : IOException ${ex.message}"
            }
            is SecurityException -> {
                downloadStatus = DownloadStatus.PERMISSION_ERROR
                "doInBackground : SecurityException ${ex.message}"
            }
            else -> {
                downloadStatus = DownloadStatus.ERROR
                "Unknown error"
            }
        }
        Log.e(TAG, errorMessage)
        return errorMessage
    }
}

 fun getFlickerJsonData(vararg params: String): ArrayList<Photo> {
    val photoList = ArrayList<Photo>()
    try {
        val jsonData = JSONObject(params[0])
        val jsonArray = jsonData.getJSONArray("items")

        for (i in 0 until jsonArray.length()) {
            val jsonPhoto = jsonArray.getJSONObject(i)

            val title = jsonPhoto.getString("title")
            val author = jsonPhoto.getString("author")
            val authorId = jsonPhoto.getString("author_id")
            val tags = jsonPhoto.getString("tags")

            val jsonMedia = jsonPhoto.getJSONObject("media")
            val photoUrl = jsonMedia.getString("m")
            val link = photoUrl.replaceFirst("_m.jpg", "_b.jpg")

            val photoObject = Photo(title, author, authorId, link, tags, photoUrl)

            photoList.add(photoObject)


//                Log.d(TAG, "doInBackground: $photoObject")
        }
    } catch (ex: JSONException) {
        ex.printStackTrace()
//            Log.e(TAG, "doInBackground: ${ex.message}" )
    }

//        Log.d(TAG, "doInBackground: ends")

    return photoList
}


