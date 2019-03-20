package com.example.kotlinproject.service.Retrofit.Datas.PhotoSize

import com.google.gson.annotations.SerializedName

data class Sizes(

	@field:SerializedName("size")
	val size: List<SizeItem?>? = null,

	@field:SerializedName("canprint")
	val canprint: Int? = null,

	@field:SerializedName("canblog")
	val canblog: Int? = null,

	@field:SerializedName("candownload")
	val candownload: Int? = null
)