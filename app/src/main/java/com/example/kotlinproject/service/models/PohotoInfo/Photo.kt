package com.example.kotlinproject.service.Retrofit.Datas.PohotoInfo

import com.google.gson.annotations.SerializedName

data class Photo(

	@field:SerializedName("server")
	val server: String? = null?:"",

	@field:SerializedName("dateuploaded")
	val dateuploaded: String? = null?:"",

	@field:SerializedName("notes")
	val notes: Notes? = null,

	@field:SerializedName("safety_level")
	val safetyLevel: String? = null?:"",

	@field:SerializedName("usage")
	val usage: Usage? = null,

	@field:SerializedName("description")
	val description: Description? = null,

	@field:SerializedName("secret")
	val secret: String? = null?:"",

	@field:SerializedName("media")
	val media: String? = null?:"",

	@field:SerializedName("title")
	val title: Title? = null,

	@field:SerializedName("urls")
	val urls: Urls? = null,

	@field:SerializedName("editability")
	val editability: Editability? = null,

	@field:SerializedName("farm")
	val farm: Int? = null,

	@field:SerializedName("id")
	val id: String? = null?:"",

	@field:SerializedName("views")
	val views: String? = null?:"",

	@field:SerializedName("owner")
	val owner: Owner? = null,

	@field:SerializedName("geoperms")
	val geoperms: Geoperms? = null,




	@field:SerializedName("publiceditability")
	val publiceditability: Publiceditability? = null,

	@field:SerializedName("rotation")
	val rotation: Int? = null,

	@field:SerializedName("dates")
	val dates: Dates? = null,

	@field:SerializedName("people")
	val people: People? = null,


	@field:SerializedName("license")
	val license: String? = null?:"",

	@field:SerializedName("isfavorite")
	val isfavorite: Int? = null,

	@field:SerializedName("location")
	val location: Location? = null
)