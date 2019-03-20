package com.example.kotlinproject.database.database

import android.arch.lifecycle.LiveData
import android.support.annotation.WorkerThread

class Repository(private val dao: DAO) {

    val allUser: LiveData<List<User>> = dao.getAllUsers()
    val allCategory: LiveData<List<Category>> = dao.getAllCategories()
    val allFavorite: LiveData<List<Favorite>> = dao.getAllFavorites()


    @WorkerThread
    fun insertUser(user: User) {
        dao.insertUser(user)
    }

    @WorkerThread
    fun insertCategory(category: Category) {
        dao.insertCategory(category)
    }


    @WorkerThread
    fun insertFavorie(favorite: Favorite) {
        dao.insertFavorite(favorite)
    }

    @WorkerThread
    fun deleteFavorite(favorite_id:Int){
        dao.deleteFavorite(favorite_id)
    }

}