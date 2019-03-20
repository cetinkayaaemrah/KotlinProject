package com.example.kotlinproject.service.Retrofit.Datas.PohotoInfo

import com.google.gson.annotations.SerializedName

data class Owner(

    @field:SerializedName("nsid")
    val nsid: String? = null ?: "",

    @field:SerializedName("iconfarm")
    val iconfarm: Int? = null,

    @field:SerializedName("path_alias")
    val pathAlias: String? = null ?: "",

    @field:SerializedName("iconserver")
    val iconserver: String? = null ?: "",

    @field:SerializedName("location")
    val location: String? = null ?: "",

    @field:SerializedName("username")
    val username: String? = null ?: "",

    @field:SerializedName("realname")
    val realname: String? = null ?: ""
)