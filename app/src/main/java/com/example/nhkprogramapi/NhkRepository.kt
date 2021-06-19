package com.example.nhkprogramapi

import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class NhkRepository {

    suspend fun getProgramTitle(): Result<String>{

        return kotlin.runCatching {
            suspendCoroutine<String> { continuation ->
                GlobalScope.launch {
                    val nhkService = returnService()
                    val response = nhkService.getProgramInfo("130", "e1", "2021-06-19", "nEltUYm0D37087JcmXSnbNVYZEnGm1mY")
                    if (response.isSuccessful) {
                        val article = response.body()
                        Log.d("debug", article?.list?.e1?.get(1)?.title ?: "ぬる")
                        continuation.resume(article?.list?.e1?.get(1)?.title.toString())
                    } else{
                        val e: Exception = IllegalAccessException()
                        continuation.resumeWithException(e)
                    }

                }
            }



        }
    }


    private fun returnService(): NhkService{
        return Retrofit.Builder()
            .baseUrl("https://api.nhk.or.jp/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(NhkService::class.java)
    }
}