package com.example.kotlinproject.service.Retrofit.Datas.PohotoInfo

import com.google.gson.annotations.SerializedName

data class Editability(

	@field:SerializedName("cancomment")
	val cancomment: Int? = null,

	@field:SerializedName("canaddmeta")
	val canaddmeta: Int? = null
)