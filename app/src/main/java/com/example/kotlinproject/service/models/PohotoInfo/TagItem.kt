package com.example.kotlinproject.service.Retrofit.Datas.PohotoInfo

import com.google.gson.annotations.SerializedName

data class TagItem(

	@field:SerializedName("author")
	val author: String? = null?:"",

	@field:SerializedName("machine_tag")
	val machineTag: Int? = null,

	@field:SerializedName("authorname")
	val authorname: String? = null?:"",

	@field:SerializedName("raw")
	val raw: String? = null?:"",

	@field:SerializedName("id")
	val id: String? = null?:"",

	@field:SerializedName("_content")
	val content: String? = null?:""
)