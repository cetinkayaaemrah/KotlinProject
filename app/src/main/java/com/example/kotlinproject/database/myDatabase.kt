package com.example.kotlinproject.database.database

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//exportSchema = false
@Database(entities = [User::class, Favorite::class, Category::class], version = 2)
abstract class myDatabase : RoomDatabase() {

    abstract fun dao(): DAO


    companion object {
        @Volatile
        private var INSTANCE: myDatabase? = null
        fun getDatabase(context: Context, scope: CoroutineScope): myDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    myDatabase::class.java,
                    "user_database"
                ).fallbackToDestructiveMigration()
                    .addCallback(Companion.DatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class DatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.dao())
                    }
                }
            }
        }

        fun populateDatabase(dao: DAO) {
            dao.deleteAllUser()


            var user = User(0, "emrahcetinkayaa@hotmail.com", "Emrah", "Cetinkaya", "asdasd")
            dao.insertUser(user)
            user = User(0, "emrahcetinkayaa@hotmail.com", "1", "Cetinkaya", "1")
            dao.insertUser(user)



        /*    var category = Category(0, "Select Category")
            dao.insertCategory(category)
            category = Category(0, "Art")
            dao.insertCategory(category)
            category = Category(0, "Nature")
            dao.insertCategory(category)
            category = Category(0, "Tecnology")
            dao.insertCategory(category)*/


        }
    }

}