package com.sample.imagesearch.api

import android.util.ArrayMap
import com.sample.imagesearch.model.ImageDataModel
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiInterface {

    @GET("colors")
    fun getImageResults(@QueryMap map: ArrayMap<String,String>) : Call<List<ImageDataModel>>

    companion object {

        var BASE_URL = "https://www.colourlovers.com/api/"

        fun create() : ApiInterface {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)

        }
    }
}