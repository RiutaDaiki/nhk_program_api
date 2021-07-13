package com.example.nhkprogramapi.entity


import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NhkBsPremium {
    @GET("/v2/pg/list/{area}/{service}/{date}.json")
    suspend fun getProgramInfo(
        @Path("area") area: String,
        @Path("service") service: String,
        @Path("date") date: String,
        @Query("key") apiKey: String

    ): Response<BsPremiumResponse>
}

data class BsPremiumResponse(
    val list: S3
)

data class S3(
    val s3: List<BsPremiumTitle>
)

data class BsPremiumTitle(
    val title: String,
)