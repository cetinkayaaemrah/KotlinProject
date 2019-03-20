package com.example.kotlinproject.service.Retrofit.Datas.PhotoSearch

import com.google.gson.annotations.SerializedName

data class PhotoSearch(

	@field:SerializedName("stat")
	val stat: String? = null?:"",

	@field:SerializedName("photos")
	val photos: Photos? = null
)