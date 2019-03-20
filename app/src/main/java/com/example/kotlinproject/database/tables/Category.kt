package com.example.kotlinproject.database.database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull

@Entity(tableName = "categories")
class Category(
    @NonNull
    @PrimaryKey(autoGenerate = true)
    val category_id: Int,
    val category_name: String? = null ?: ""
)