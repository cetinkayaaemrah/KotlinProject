package com.example.kotlinproject.database.database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull

@Entity(tableName = "users")
data class User(
    @NonNull
    @PrimaryKey(autoGenerate = true)
    val user_id: Int,
    val user_email: String? = null ?: "",
    val user_name: String? = null ?: "",
    val user_surname: String? = null ?: "",
    val user_password: String? = null ?: ""
)