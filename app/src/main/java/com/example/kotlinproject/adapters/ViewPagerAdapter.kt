package com.example.kotlinproject.adapters

import android.app.Activity
import android.content.Context
import android.support.v4.view.PagerAdapter
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.kotlinproject.service.Retrofit.Datas.PhotoInformations
import com.example.kotlinproject.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.squareup.picasso.Picasso
import java.lang.Double


class ViewPagerAdapter(
    private val activity: Activity,
    val imageUrls: ArrayList<String>,
    private val mMap: GoogleMap,
    private val title: TextView,
    private var photoInformations: List<PhotoInformations>?
) : PagerAdapter() {

    lateinit var inflater: LayoutInflater
    lateinit var itemView: View
    lateinit var vpImageView: ImageView

    override fun isViewFromObject(p0: View, p1: Any): Boolean {
        return p0 == p1
    }


    override fun getCount(): Int {
        return imageUrls.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        inflater = activity.applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        itemView = inflater.inflate(R.layout.viewpager_item, container, false)

        vpImageView = itemView.findViewById(R.id.imageViewVp)
        var disp = DisplayMetrics()
        var height: Int = disp.heightPixels
        var width: Int = disp.widthPixels
        vpImageView.setMinimumHeight(height)
        vpImageView.setMinimumWidth(width)

        val pos = getImagePosition(imageUrls[position]) // imageUrl incele

        if (title != null) {
            title.setText(photoInformations?.get(position)?.title)
        }

        if (mMap != null) {
            mMap.clear()
            val location = LatLng(
                Double.parseDouble(photoInformations?.get(pos)?.latitude),
                Double.parseDouble(photoInformations?.get(pos)?.latitude)
            )
            val a = MarkerOptions().position(location)
            val m = mMap.addMarker(a)
            m.setPosition(location)
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 10f))
        }

        Picasso.get()
            .load(imageUrls[position])
            .placeholder(R.drawable.progress_animation)
            .error(R.drawable.ic_error)
            .into(vpImageView)

        container.addView(itemView)
        return itemView

    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }

    private fun getImagePosition(url: String): Int {
        for (i in imageUrls.indices) {
            if (imageUrls[i] == url) {
                return i
            }
        }
        return 0
    }

    fun setPhotoList(photoList: ArrayList<PhotoInformations>?) {
        photoInformations = photoList!!
        imageUrls.add(photoList.last().imgUrlHD.toString())
    }


}

