package com.example.nhkprogramapi

import com.example.nhkprogramapi.entity.NhkBs1
import com.example.nhkprogramapi.entity.NhkBsPremium
import com.example.nhkprogramapi.entity.NhkEtele
import com.example.nhkprogramapi.entity.NhkSougou
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class NhkRepository {

    suspend fun getSougouProgramTitle(date: String): Result<String>{

            return kotlin.runCatching {
                suspendCoroutine<String> { continuation ->
                    GlobalScope.launch {

                        val response = returnSougouService().getProgramInfo("190", "g1", date, BuildConfig.API_KEY)
                        if (response.isSuccessful) {
                            val article = response.body()
                            continuation.resume(article?.list?.g1?.get(0)?.title.toString())
                        } else{
                            val e: Exception = IllegalAccessException()
                            continuation.resumeWithException(e)
                        }
                    }
                }
            }
    }

    suspend fun getEteleProgramTitle(date: String): Result<String>{

        return kotlin.runCatching {
            suspendCoroutine<String> { continuation ->
                GlobalScope.launch {

                    val response = returnEteleService().getProgramInfo("190", "e1", date, BuildConfig.API_KEY)
                    if (response.isSuccessful) {
                        val article = response.body()
                        continuation.resume(article?.list?.e1?.get(0)?.title.toString())
                    } else{
                        val e: Exception = IllegalAccessException()
                        continuation.resumeWithException(e)
                    }
                }
            }
        }
    }

    suspend fun getBsProgramTitle(date: String): Result<String>{

        return kotlin.runCatching {
            suspendCoroutine<String> { continuation ->
                GlobalScope.launch {

                    val response = returnBsService().getProgramInfo("190", "s1", date, BuildConfig.API_KEY)
                    if (response.isSuccessful) {
                        val article = response.body()
                        continuation.resume(article?.list?.s1?.get(0)?.title.toString())
                    } else{
                        val e: Exception = IllegalAccessException()
                        continuation.resumeWithException(e)
                    }
                }
            }
        }
    }

    suspend fun getBsPremiumProgramTitle(date: String): Result<String>{

        return kotlin.runCatching {
            suspendCoroutine<String> { continuation ->
                GlobalScope.launch {

                    val response = returnBsPremiumService().getProgramInfo("190", "s3", date, BuildConfig.API_KEY)
                    if (response.isSuccessful) {
                        val article = response.body()
                        continuation.resume(article?.list?.s3?.get(0)?.title.toString())
                    } else{
                        val e: Exception = IllegalAccessException()
                        continuation.resumeWithException(e)
                    }
                }
            }
        }
    }

    private fun returnSougouService(): NhkSougou{
        return Retrofit.Builder()
            .baseUrl("https://api.nhk.or.jp/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(NhkSougou::class.java)
    }

    private fun returnEteleService(): NhkEtele{
        return Retrofit.Builder()
            .baseUrl("https://api.nhk.or.jp/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(NhkEtele::class.java)
    }

    private fun returnBsService(): NhkBs1{
        return Retrofit.Builder()
            .baseUrl("https://api.nhk.or.jp/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(NhkBs1::class.java)
    }

    private fun returnBsPremiumService(): NhkBsPremium{
        return Retrofit.Builder()
            .baseUrl("https://api.nhk.or.jp/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(NhkBsPremium::class.java)
    }
}