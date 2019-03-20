package com.example.kotlinproject.database.database

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.math.log

class ViewModel(application: Application) : AndroidViewModel(application) {
    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    private val repository: Repository
    val allUsers: LiveData<List<User>>
    val allCategories: LiveData<List<Category>>
    val allFavorites: LiveData<List<Favorite>>

    init {
        Log.d("Test","(ViewModel | init())")
        val dao = myDatabase.getDatabase(application, scope).dao()
        repository = Repository(dao)
        allUsers = repository.allUser
        allCategories = repository.allCategory
        allFavorites = repository.allFavorite

    }

    fun insertUser(user: User) = scope.launch(Dispatchers.IO) {
        repository.insertUser(user)
    }

    fun insertCategory(category: Category) =scope.launch (Dispatchers.IO){
        repository.insertCategory(category)
    }
    fun insertFavorite(favorite: Favorite) =scope.launch (Dispatchers.IO){
        repository.insertFavorie(favorite)
    }

    fun deleteFavorite(favorite_Id: Int)=scope.launch (Dispatchers.IO ){
        repository.deleteFavorite(favorite_Id)
    }


    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }

}