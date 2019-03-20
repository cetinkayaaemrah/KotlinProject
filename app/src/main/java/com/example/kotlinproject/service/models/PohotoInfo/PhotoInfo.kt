package com.example.kotlinproject.service.Retrofit.Datas.PohotoInfo

import com.google.gson.annotations.SerializedName

data class PhotoInfo(

	@field:SerializedName("stat")
	val stat: String? = null?:"",

	@field:SerializedName("photo")
	val photo: Photo? = null
)