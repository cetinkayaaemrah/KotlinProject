package com.example.kotlinproject.adapters

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.example.kotlinproject.R
import com.example.kotlinproject.activities.Main2Activity
import com.example.kotlinproject.database.database.ViewModel
import com.example.kotlinproject.database.models.FavoriteModel
import com.squareup.picasso.Picasso


class RecyclerviewAdapterFavorite(val context: Context, val favorites: ArrayList<FavoriteModel>) :
    RecyclerView.Adapter<RecyclerviewAdapterFavorite.ViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val itemView = inflater.inflate(R.layout.carview_favorite_layout, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = favorites.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = favorites[position]
        holder.tvId.text = current.favorite_id.toString()
        holder.tvCategory.text = current.category_name
        holder.tvTitle.text = current.title
        holder.tvDescription.text = current.description

        holder.ibtnDelete.setOnClickListener {
            var mViewModel: ViewModel = ViewModelProviders.of(context as Main2Activity).get(ViewModel::class.java)
            mViewModel.deleteFavorite(current.favorite_id)

            favorites.remove(favorites[position])
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, favorites.size)

        }

        Picasso.get()
            .load(current.imgUrl)
            .placeholder(R.drawable.loading_image)
            .error(R.drawable.abc_ab_share_pack_mtrl_alpha)
            .into(holder.ivSmalImage)



    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivSmalImage = itemView.findViewById<ImageView>(R.id.ivSmallImage)
        val ibtnDelete = itemView.findViewById<ImageButton>(R.id.ibtnDelete)
        val tvCategory = itemView.findViewById<TextView>(R.id.tvCategory)
        val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        val tvId = itemView.findViewById<TextView>(R.id.tvFavoriId)
        val tvDescription = itemView.findViewById<TextView>(R.id.tvDescription)

    }

}