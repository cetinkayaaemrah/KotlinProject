package com.example.kotlinproject.service.Retrofit.Datas.PhotoSize

import com.google.gson.annotations.SerializedName

data class SizeItem(

	@field:SerializedName("width")
	val width: Int? = null,

	@field:SerializedName("label")
	val label: String? = null ?:"",

	@field:SerializedName("source")
	val source: String? = null?:"",

	@field:SerializedName("media")
	val media: String? = null?:"",

	@field:SerializedName("url")
	val url: String? = null?:"",

	@field:SerializedName("height")
	val height: Int? = null
)