package com.example.nhkprogramapi

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NhkService {
    @GET("/v2/pg/list/{area}/{service}/{date}.json")
    suspend fun getProgramInfo(
        @Path("area") area: String,
        @Path("service") service: String,
        @Path("date") date: String,
        @Query("key") apiKey: String

    ): Response<ProgramResponse>
}

data class ProgramResponse(
    val list: E1
)

data class E1(
    val g1: List<Title>
)

data class Title(
    val title: String,
)