package com.example.nhkprogramapi

import com.example.nhkprogramapi.entity.NhkBsPremium
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class NhkRepository {

    suspend fun getProgramTitle(date: String): Result<String>{

        return kotlin.runCatching {
            suspendCoroutine<String> { continuation ->
                GlobalScope.launch {
                    val nhkService = returnService()
                    val response = nhkService.getProgramInfo("190", "s3", date, BuildConfig.API_KEY)
                    if (response.isSuccessful) {
                        val article = response.body()
                        continuation.resume(article?.list?.s3?.get(25)?.title.toString())
                    } else{
                        val e: Exception = IllegalAccessException()
                        continuation.resumeWithException(e)
                    }
                }
            }
        }
    }

    private fun returnService(): NhkBsPremium{
        return Retrofit.Builder()
            .baseUrl("https://api.nhk.or.jp/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(NhkBsPremium::class.java)
    }
}