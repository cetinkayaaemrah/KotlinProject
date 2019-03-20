package com.example.kotlinproject.adapters

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.example.kotlinproject.R
import com.example.kotlinproject.database.database.Category
import com.example.kotlinproject.database.models.CategoryModel
import java.util.ArrayList

class SpinnerCustomAdapter(var categoryList: List<Category>, val activity: Activity) : BaseAdapter() {
    var inflater:LayoutInflater
    init {
        inflater= (activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?)!!
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view: View? = convertView
        if(convertView==null){
            view=inflater.inflate(R.layout.spiner_custom,null)
        }
        val tvSpinner=view?.findViewById<TextView>(R.id.tv_spinner)
        tvSpinner?.text=categoryList[position].category_name
        return view!!
    }

    override fun getItem(position: Int): Any? {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return categoryList.size
    }

    internal fun setCategories(categoryList: List<Category>) {
        this.categoryList = categoryList
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view= super.getDropDownView(position, convertView, parent)
        val ll:LinearLayout= view as LinearLayout
        val tvCategory=ll.findViewById<TextView>(R.id.tv_spinner)
        tvCategory.gravity = Gravity.LEFT
        tvCategory.setTextColor(Color.parseColor("#333639"))
        tvCategory.layoutParams=LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)
        return view
    }
}