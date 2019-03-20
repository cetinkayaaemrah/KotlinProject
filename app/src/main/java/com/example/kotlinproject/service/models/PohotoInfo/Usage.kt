package com.example.kotlinproject.service.Retrofit.Datas.PohotoInfo

import com.google.gson.annotations.SerializedName

data class Usage(

	@field:SerializedName("canshare")
	val canshare: Int? = null,

	@field:SerializedName("canprint")
	val canprint: Int? = null,

	@field:SerializedName("canblog")
	val canblog: Int? = null,

	@field:SerializedName("candownload")
	val candownload: Int? = null
)