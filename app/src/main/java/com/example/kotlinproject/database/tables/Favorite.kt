package com.example.kotlinproject.database.database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import org.jetbrains.annotations.NotNull


@Entity(tableName = "favorites")
@ForeignKey(entity = Category::class, parentColumns = arrayOf("category_id"), childColumns = arrayOf("category_id"))

class Favorite(
    @NotNull
    @PrimaryKey(autoGenerate = true)
    val favorite_id: Int,

    val category_id: Int ,

    val photo_id: String = null ?: "",

    val title: String? = null ?: "",

    val description: String? = null ?: "",

    val imgUrl: String? = null ?: "",

    val imgUrlHD: String? = null ?: "",

    val latitude: String? = null ?: "",

    val longitude: String? = null ?: ""
)