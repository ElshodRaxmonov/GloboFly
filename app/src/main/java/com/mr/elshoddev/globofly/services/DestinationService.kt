package com.mr.elshoddev.globofly.services

import com.mr.elshoddev.globofly.helpers.DestinationAdapter
import com.mr.elshoddev.globofly.models.Destination
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface DestinationService {


    @GET("destination")
    fun getDestinationList(): Call<List<Destination>>

/** Headers is essantial information to work with server.
    Cause Headers is important to share information value to server to perform any operation
 **/

    /**
    @GET("destination")

    fun getDestinationList(@Query(country) country:String): Call<List<Destination>>
    I am gonna put many parameters on query

    fun getDestinationList(@QueryMap filer:HashMap<String,String>): Call<List<Destination>>

    and to call

    val filter=HashMap<>()
    filter["country"]=India
    filter["count"]=1
    service.getDestination(filter)

     **/

    @GET("destination/{id}")
    fun getDestination(@Path("id") id: Int): Call<Destination>

    @POST("destination")
    fun addDestination(@Body destination: Destination): Call<Destination>

    @FormUrlEncoded
    @PUT("destination/{id}")
    fun updateDestination(
        @Path("id") id: Int,
        @Field("city") city: String,
        @Field("country") country: String,
        @Field("description") desc: String,
        @Field("image") image: String,
    ): Call<Destination>

    @DELETE("destination/{id}")
    fun deleteDestination(@Path("id") id: Int): Call<Unit>

}