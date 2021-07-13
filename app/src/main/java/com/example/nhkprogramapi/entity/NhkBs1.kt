package com.example.nhkprogramapi.entity


import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NhkBs1 {
    @GET("/v2/pg/list/{area}/{service}/{date}.json")
    suspend fun getProgramInfo(
        @Path("area") area: String,
        @Path("service") service: String,
        @Path("date") date: String,
        @Query("key") apiKey: String

    ): Response<Bs1Response>
}

data class Bs1Response(
    val list: S1
)

data class S1(
    val s1: List<Title>
)

data class Bs1Title(
    val title: String,
)