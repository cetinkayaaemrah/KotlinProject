package com.example.kotlinproject.fragments


import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.Spinner
import com.example.kotlinproject.adapters.RecyclerviewAdapterFavorite

import com.example.kotlinproject.R
import com.example.kotlinproject.activities.Main2Activity
import com.example.kotlinproject.adapters.SpinnerCustomAdapter
import com.example.kotlinproject.database.database.Category
import com.example.kotlinproject.database.database.Favorite
import com.example.kotlinproject.database.database.ViewModel
import com.example.kotlinproject.database.models.FavoriteModel
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_favorite.*
import kotlinx.android.synthetic.main.fragment_search.*


class FragmentFavorite : Fragment() {
    private lateinit var mAdapterFavorite: RecyclerviewAdapterFavorite
    private var favoriteModel = ArrayList<FavoriteModel>()
    private lateinit var mView: View
    var favorites = emptyList<Favorite>()
    var categoryList = emptyList<Category>()


    companion object {
        fun newInstance(): FragmentFavorite {
            val fragmentFavorite = FragmentFavorite()
            val args = Bundle()
            fragmentFavorite.arguments = args
            return fragmentFavorite
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_favorite, container, false)
        val mViewModel = ViewModelProviders.of(context as Main2Activity).get(ViewModel::class.java)
        mViewModel.allCategories.observe(this, Observer { category ->
            category.let { it ->
                categoryList = it!!
            }
        })
        mViewModel.allFavorites.observe(this, Observer { favorite ->
            favorite.let {
                favorites = it!!
                init()
            }
        })

        return mView
    }

    private fun init() {
        buildFavoriteModel()

        var spinner = mView.findViewById<Spinner>(R.id.spinnerFavorite)
        var spinnerAdapter = SpinnerCustomAdapter(categoryList, context as Activity)
        spinner.adapter = spinnerAdapter
        spinnerAdapter.setCategories(categoryList)
        spinnerAdapter.notifyDataSetChanged()

        var recyclerViewFavorite = mView.findViewById<RecyclerView>(R.id.recyclerViewFavorite)
        recyclerViewFavorite.layoutManager =
            LinearLayoutManager(context, LinearLayout.VERTICAL, false) as RecyclerView.LayoutManager?
        recyclerViewFavorite.setHasFixedSize(true)
        mAdapterFavorite = RecyclerviewAdapterFavorite(context!!, favoriteModel)
        recyclerViewFavorite.adapter = (mAdapterFavorite)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p2 != 0) {
                    var filteredFavorites = ArrayList<FavoriteModel>()
                    for (i in favoriteModel) {
                        if (categoryList[p2].category_name.equals(i.category_name)) {
                            filteredFavorites.add(
                                FavoriteModel(
                                    i.favorite_id,
                                    i.category_name,
                                    i.photo_id,
                                    i.title,
                                    i.description,
                                    i.imgUrl,
                                    i.imgUrlHD,
                                    i.latitude,
                                    i.longitude
                                )
                            )
                        }
                    }
                    mAdapterFavorite = RecyclerviewAdapterFavorite(context!!, filteredFavorites)
                    recyclerViewFavorite.adapter = (mAdapterFavorite)
                }
                if(p2==0){
                    mAdapterFavorite = RecyclerviewAdapterFavorite(context!!, favoriteModel)
                    recyclerViewFavorite.adapter = (mAdapterFavorite)
                }
            }
        }
    }

    private fun buildFavoriteModel() {
        for (i in favorites) {
            favoriteModel.add(
                FavoriteModel(
                    i.favorite_id,
                    getCategoryName(i.category_id),
                    i.photo_id,
                    i.title,
                    i.description,
                    i.imgUrl,
                    i.imgUrlHD,
                    i.latitude,
                    i.longitude
                )
            )
        }

    }

    private fun getCategoryName(category_id: Int): String {
        for (i in categoryList) {
            if (i.category_id == category_id) {
                return i.category_name ?: ""
            }
        }
        return ""
    }


}
