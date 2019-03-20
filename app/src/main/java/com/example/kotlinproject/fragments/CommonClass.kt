package com.example.kotlinproject.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import com.example.kotlinproject.database.database.Category
import com.example.kotlinproject.database.database.Favorite
import com.example.kotlinproject.database.database.ViewModel
import com.example.kotlinproject.database.models.CategoryModel
import com.example.kotlinproject.database.models.FavoriteIdModel
import java.util.ArrayList

class CommonClass() {

    companion object {
        private lateinit var mViewModel: ViewModel

        fun getCategoriesModel(appCompactActivity: AppCompatActivity): ArrayList<CategoryModel> {
            var category = emptyList<Category>()
            var categoryList = ArrayList<CategoryModel>()
            mViewModel = ViewModelProviders.of(appCompactActivity).get(ViewModel::class.java)
            mViewModel.allCategories.observe(appCompactActivity, Observer { categories ->
                categories?.let { category = it }
            })

            for (i in category) {
                categoryList.add(CategoryModel(i.category_id, i.category_name.toString()))
            }
            return categoryList
        }

        fun isFavoriteExist(photo_id: String, favorites: List<Favorite>): Boolean {
            for (i in favorites) {
                if (photo_id.equals(i.photo_id)) {
                    return true
                }
            }
            return false
        }

        fun getFavoritesForCompare(appCompactActivity: AppCompatActivity): ArrayList<FavoriteIdModel> {
            var favorites = emptyList<Favorite>()
            var favoriteIdList = ArrayList<FavoriteIdModel>()
            mViewModel = ViewModelProviders.of(appCompactActivity).get(ViewModel::class.java)
            mViewModel.allFavorites.observe(appCompactActivity, Observer { favorite ->
                favorite?.let { favorites = it }
            })
            for (i in favorites) {
                favoriteIdList.add(FavoriteIdModel(i.favorite_id, i.photo_id))
            }
            return favoriteIdList
        }

        fun getFavoriteIdByPhotoId(photo_id: String, favorites: List<Favorite>): Int {
            for (i in favorites) {
                if (photo_id.equals(i.photo_id)) {
                    return i.favorite_id
                }
            }
            return -1
        }


        fun getFavorites(appCompactActivity: AppCompatActivity): List<Favorite> {
            var favorites = emptyList<Favorite>()
            mViewModel = ViewModelProviders.of(appCompactActivity).get(ViewModel::class.java)
            mViewModel.allFavorites.observe(appCompactActivity, Observer { favorite ->
                favorite?.let { favorites = it }
            })
            return favorites
        }

        fun getCategories(appCompactActivity: AppCompatActivity): List<Category> {
            var categories = emptyList<Category>()
            mViewModel = ViewModelProviders.of(appCompactActivity).get(ViewModel::class.java)
            mViewModel.allCategories.observe(appCompactActivity, Observer { category ->
                category?.let { categories = it }
            })
            return categories
        }

    }


}
