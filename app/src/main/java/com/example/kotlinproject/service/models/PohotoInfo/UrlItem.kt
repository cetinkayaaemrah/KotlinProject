package com.example.kotlinproject.service.Retrofit.Datas.PohotoInfo

import com.google.gson.annotations.SerializedName

data class UrlItem(

	@field:SerializedName("type")
	val type: String? = null?:"",

	@field:SerializedName("_content")
	val content: String? = null?:""
)