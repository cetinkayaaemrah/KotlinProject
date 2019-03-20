package com.example.kotlinproject.service.Retrofit.Datas.PhotoSize

import com.google.gson.annotations.SerializedName

data class Images(

	@field:SerializedName("stat")
	val stat: String? = null?:"",

	@field:SerializedName("sizes")
	val sizes: Sizes? = null
)