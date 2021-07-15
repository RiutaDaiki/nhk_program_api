package com.example.nhkprogramapi.entity

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NhkEtele {
    @GET("/v2/pg/list/{area}/{service}/{date}.json")
    suspend fun getProgramInfo(
        @Path("area") area: String,
        @Path("service") service: String,
        @Path("date") date: String,
        @Query("key") apiKey: String

    ): Response<EteleResponse>
}

data class EteleResponse(
    val list: E1
)

data class E1(
    val e1: List<EteleTitle>
)

data class EteleTitle(
    val start_time: String,
    val end_time: String,
    val title: String
    )