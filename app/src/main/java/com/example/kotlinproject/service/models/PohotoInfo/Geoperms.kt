package com.example.kotlinproject.service.Retrofit.Datas.PohotoInfo

import com.google.gson.annotations.SerializedName

data class Geoperms(

	@field:SerializedName("ispublic")
	val ispublic: Int? = null,

	@field:SerializedName("isfriend")
	val isfriend: Int? = null,

	@field:SerializedName("iscontact")
	val iscontact: Int? = null,

	@field:SerializedName("isfamily")
	val isfamily: Int? = null
)