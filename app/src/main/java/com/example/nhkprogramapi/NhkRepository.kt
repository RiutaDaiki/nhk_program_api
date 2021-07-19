package com.example.nhkprogramapi

import com.example.nhkprogramapi.entity.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class NhkRepository {

    suspend fun getSougouProgramTitle(date: String): Result<List<ProgramInfo>>{

            return kotlin.runCatching {
                suspendCoroutine<List<ProgramInfo>> { continuation ->
                    GlobalScope.launch {

                        val response = returnSougouService().getProgramInfo("130", "g1", date, BuildConfig.API_KEY)
                        if (response.isSuccessful) {
                            val article = response.body()
                            val result = mutableListOf<ProgramInfo>()
                            if (article?.list?.g1?.size != null){
                                for (i in article.list.g1.indices)
                                    result.add(i, ProgramInfo(
                                        article.list.g1.get(i).start_time,
                                        article.list.g1.get(i).end_time,
                                        article.list.g1.get(i).title))
                            }
                            continuation.resume(result)
                        } else{
                            val e: Exception = IllegalAccessException()
                            continuation.resumeWithException(e)
                        }
                    }
                }
            }
    }

    suspend fun getEteleProgramTitle(date: String): Result<List<ProgramInfo>>{

        return kotlin.runCatching {
            suspendCoroutine<List<ProgramInfo>> { continuation ->
                GlobalScope.launch {

                    val response = returnEteleService().getProgramInfo("130", "e1", date, BuildConfig.API_KEY)
                    if (response.isSuccessful) {
                        val article = response.body()
                        val result = mutableListOf<ProgramInfo>()
                        if (article?.list?.e1?.size != null){
                            for (i in article.list.e1.indices)
                                result.add(i, ProgramInfo(
                                    article.list.e1.get(i).start_time,
                                    article.list.e1.get(i).end_time,
                                    article.list.e1.get(i).title))
                        }
                        continuation.resume(result)
                    } else{
                        val e: Exception = IllegalAccessException()
                        continuation.resumeWithException(e)
                    }
                }
            }
        }
    }

    suspend fun getBsProgramTitle(date: String): Result<List<ProgramInfo>>{

        return kotlin.runCatching {
            suspendCoroutine<List<ProgramInfo>> { continuation ->
                GlobalScope.launch {

                    val response = returnBsService().getProgramInfo("130", "s1", date, BuildConfig.API_KEY)
                    if (response.isSuccessful) {
                        val article = response.body()
                        val result = mutableListOf<ProgramInfo>()
                        if (article?.list?.s1?.size != null){
                            for (i in article.list.s1.indices)
                                result.add(i, ProgramInfo(
                                    article.list.s1.get(i).start_time,
                                    article.list.s1.get(i).end_time,
                                    article.list.s1.get(i).title))
                        }
                        continuation.resume(result)
                    } else{
                        val e: Exception = IllegalAccessException()
                        continuation.resumeWithException(e)
                    }
                }
            }
        }
    }

    suspend fun getBsPremiumProgramTitle(date: String): Result<List<ProgramInfo>>{

        return kotlin.runCatching {
            suspendCoroutine<List<ProgramInfo>> { continuation ->
                GlobalScope.launch {

                    val response = returnBsPremiumService().getProgramInfo("130", "s3", date, BuildConfig.API_KEY)
                    if (response.isSuccessful) {
                        val article = response.body()
                        val result = mutableListOf<ProgramInfo>()
                        if (article?.list?.s3?.size != null){
                            for (i in article.list.s3.indices)
                                result.add(i, ProgramInfo(
                                    article.list.s3.get(i).start_time,
                                    article.list.s3.get(i).end_time,
                                    article.list.s3.get(i).title))
                        }
                        continuation.resume(result)
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

//"start_time": "2021-07-14T04:32:00+09:00",
//"end_time": "2021-07-14T04:35:00+09:00",