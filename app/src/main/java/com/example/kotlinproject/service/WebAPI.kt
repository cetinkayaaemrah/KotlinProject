package com.example.kotlinproject.service.Retrofit

import com.example.kotlinproject.service.Retrofit.Datas.PhotoSearch.PhotoSearch
import com.example.kotlinproject.service.Retrofit.Datas.PhotoSize.Images
import com.example.kotlinproject.service.Retrofit.Datas.PohotoInfo.PhotoInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WebAPI {

    val BASE_URL: String
        get() = "https://api.flickr.com/services/"


    @GET("rest/")
    fun getPhotoSearch(
        @Query("method") method: String,
        @Query("api_key") api_key: String,
        @Query("text") text: String,
        @Query("has_geo") has_geo: String,
        @Query("per_page") per_page: String,
        @Query("page") page: String,
        @Query("format") format: String,
        @Query("nojsoncallback") nojsoncallback: String
    ): Call<PhotoSearch>


    @GET("rest/")
    fun getPhotoInfo(
        @Query("method") method: String,
        @Query("api_key") api_key: String,
        @Query("photo_id") photo_id: String,
        @Query("format") format: String,
        @Query("nojsoncallback") nojsoncallback: String
    ): Call<PhotoInfo>

    @GET("rest/")
    fun getImages(
        @Query("method") method: String,
        @Query("api_key") api_key: String,
        @Query("photo_id") photo_id: String,
        @Query("format") format: String,
        @Query("nojsoncallback") nojsoncallback: String

    ): Call<Images>


}
