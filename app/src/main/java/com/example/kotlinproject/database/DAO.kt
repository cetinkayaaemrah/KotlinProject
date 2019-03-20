package com.example.kotlinproject.database.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface DAO {


    // Users
    @Insert
    fun insertUser(user: User)

    @Query("DELETE FROM users")
    fun deleteAllUser()

    @Query("SELECT * FROM users ORDER BY user_id ASC")
    fun getAllUsers(): LiveData<List<User>>


    // Categories
    @Insert
    fun insertCategory(category: Category)

    @Query("DELETE FROM categories")
    fun deleteCategory()

    @Query("SELECT * FROM categories")
    fun getAllCategories(): LiveData<List<Category>>


    //Favorites
    @Insert
    fun insertFavorite(favorite: Favorite)

    @Query("DELETE FROM favorites")
    fun deleteFavorites()

    @Query("SELECT * FROM favorites ORDER BY favorite_id DESC")
    fun getAllFavorites(): LiveData<List<Favorite>>

    @Query("DELETE FROM favorites WHERE favorite_id=:favoriteId")
    fun deleteFavorite(favoriteId:Int)

}