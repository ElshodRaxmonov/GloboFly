package com.mr.elshoddev.globofly.services

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface MessageService {
    @GET
    fun getMessages(@Url url: String): Call<String>
}