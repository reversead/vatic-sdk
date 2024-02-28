package com.vaticai.postback

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody

enum class LoanStatus(val code: Int) {
    RECEIVED(0),
    APPROVED(1),
    DISBURSED(2),
    DEFAULT(3);
}

data class Payload(
    @SerializedName("partner_id")
    val partnerId: String,
    val maid: String?,
    val status: Int)

class Postback(private val context: Context, private val partnerId: String) {

    val url = "https://postback.vaticai.com/postback"

    private suspend fun makeHttpPostCall(url: String, requestBody: RequestBody): String {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()
        val response = withContext(Dispatchers.IO) {
            client.newCall(request).execute()
        }
        return response.body?.string() ?: ""
    }

    private suspend fun getAdvertisingId(): String? {
        try {
            val info = AdvertisingIdClient.getAdvertisingIdInfo(context) ?: return null
            return info.id
        } catch (exception: Exception) {
            Log.d("exception", exception.toString())
            return null
        }
    }

    val myCustomScope = CoroutineScope(Dispatchers.IO + Job())


    fun received() {
        myCustomScope.launch {
            val advertisingId = getAdvertisingId()
            val payload = Payload(partnerId, advertisingId, LoanStatus.RECEIVED.code)
            val jsonString = Gson().toJson(payload)
            withContext(Dispatchers.Main) {
                Log.d("JSON_PAYLOAD", jsonString)
            }
            val requestBody = RequestBody.create("application/json".toMediaTypeOrNull(), jsonString)
            makeHttpPostCall(url, requestBody)
        }
    }

    fun cleanUp() {
        myCustomScope.cancel()
    }

    fun approved() {
        myCustomScope.launch {
            val advertisingId = getAdvertisingId()
            val payload = Payload(partnerId, advertisingId, LoanStatus.APPROVED.code)
            val jsonString = Gson().toJson(payload)
            withContext(Dispatchers.Main) {
                Log.d("JSON_PAYLOAD", jsonString)
            }
            val requestBody = RequestBody.create("application/json".toMediaTypeOrNull(), jsonString)
            makeHttpPostCall(url, requestBody)
        }
    }

    fun disbursed() {
        myCustomScope.launch {
            val advertisingId = getAdvertisingId()
            val payload = Payload(partnerId, advertisingId, LoanStatus.DISBURSED.code)
            val jsonString = Gson().toJson(payload)
            withContext(Dispatchers.Main) {
                Log.d("JSON_PAYLOAD", jsonString)
            }
            val requestBody = RequestBody.create("application/json".toMediaTypeOrNull(), jsonString)
            makeHttpPostCall(url, requestBody)
        }
    }

    fun defaulted() {
        myCustomScope.launch {
            val advertisingId = getAdvertisingId()
            val payload = Payload(partnerId, advertisingId, LoanStatus.DEFAULT.code)
            val jsonString = Gson().toJson(payload)
            withContext(Dispatchers.Main) {
                Log.d("JSON_PAYLOAD", jsonString)
            }
            val requestBody = RequestBody.create("application/json".toMediaTypeOrNull(), jsonString)
            makeHttpPostCall(url, requestBody)
        }
    }
}
