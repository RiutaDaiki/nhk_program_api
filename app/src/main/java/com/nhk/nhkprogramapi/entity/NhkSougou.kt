package com.nhk.nhkprogramapi.entity

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NhkSougou {
    @GET("/v2/pg/list/{area}/{service}/{date}.json")
    suspend fun getProgramInfo(
        @Path("area") area: String,
        @Path("service") service: String,
        @Path("date") date: String,
        @Query("key") apiKey: String

    ): Response<ProgramResponse>
}

data class ProgramResponse(
    val list: G1
)

data class G1(
    val g1: List<Title>
)

data class Title(
    val start_time: String,
    val end_time: String,
    val title: String
    )