package com.example.kotlinproject.service.Retrofit.Datas.PohotoInfo

import com.google.gson.annotations.SerializedName

data class Location(


	@field:SerializedName("latitude")
	val latitude: String? = null?:"",

	@field:SerializedName("context")
	val context: String? = null?:"",

	@field:SerializedName("locality")
	val locality: Locality? = null,

	@field:SerializedName("woeid")
	val woeid: String? = null?:"",

	@field:SerializedName("accuracy")
	val accuracy: String? = null?:"",

	@field:SerializedName("region")
	val region: Region? = null,

	@field:SerializedName("place_id")
	val placeId: String? = null?:"",

	@field:SerializedName("longitude")
	val longitude: String? = null?:""
)