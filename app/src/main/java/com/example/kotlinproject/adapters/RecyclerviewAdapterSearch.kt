package com.example.kotlinproject.adapters

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.kotlinproject.common.BasisSingleton
import com.example.kotlinproject.fragments.CommonClass
import com.example.kotlinproject.fragments.FragmentSearch
import com.example.kotlinproject.service.Retrofit.Datas.PhotoInformations
import com.example.kotlinproject.R
import com.example.kotlinproject.activities.Main2Activity
import com.example.kotlinproject.activities.ShowActivity
import com.example.kotlinproject.database.database.Category
import com.example.kotlinproject.database.database.Favorite
import com.example.kotlinproject.database.database.ViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.carview_search_layout.view.*

class RecyclerviewAdapterSearch(
    val items: ArrayList<PhotoInformations>,
    val context: Context,
    val PAGE: Int,
    var favorites: List<Favorite>):
    RecyclerView.Adapter<RecyclerviewAdapterSearch.ViewHolder>() {

    lateinit var mViewModel: ViewModel

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivSmall = view.ivSmallImage!!
        val tvTitle = view.tvFavoriId!!
        val tvDescription = view.tvDescription!!
        val tvName = view.tvTitle!!
        val ibtnLike = view.ibtnLike!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.carview_search_layout,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(p0: ViewHolder, position: Int) {

        if (CommonClass.isFavoriteExist( items[position].photo_id,favorites)) {
            p0.ibtnLike.setImageResource(R.drawable.ic_like)
        }

        p0.tvName.text = items[position].realName
        p0.tvTitle.text = items[position].title
        p0.tvDescription.text = items[position].description
        Picasso.get()
            .load(items[position].imgUrl)
            .placeholder(R.drawable.loading_image)
            .error(R.drawable.abc_ab_share_pack_mtrl_alpha)
            .into(p0.ivSmall)
        p0.itemView.setOnClickListener {
            val intent = Intent(context, ShowActivity::class.java) //context.applicationContext
            intent.putParcelableArrayListExtra("listData", ArrayList(items))
            intent.putExtra("position", position.toString())
            intent.putExtra("page", PAGE)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
        p0.ibtnLike.setOnClickListener {

            if (CommonClass.isFavoriteExist(items[position].photo_id, favorites)) {
                var resultFavId = CommonClass.getFavoriteIdByPhotoId(items[position].photo_id,favorites)
                if (resultFavId != 0) {
                    mViewModel = ViewModelProviders.of(context as Main2Activity).get(ViewModel::class.java)
                    mViewModel.deleteFavorite(resultFavId)
                    p0.ibtnLike.setImageResource(R.drawable.ic_like2)
                } else
                {
                    Toast.makeText(context, "Silinecek favori bulunamadı", Toast.LENGTH_SHORT).show()
                }

            } else {
                var categoryList = emptyList<Category>()
                mViewModel= ViewModelProviders.of(context as Main2Activity).get(ViewModel::class.java)
                mViewModel.allCategories.observe(context as Main2Activity, Observer { categories ->
                    categories?.let {
                        categoryList = it
                        val fragmentSearch = FragmentSearch()
                        fragmentSearch.popUpLike(context, position, items, p0.ibtnLike,categoryList)
                    }
                })

            }
        }

    }


}



