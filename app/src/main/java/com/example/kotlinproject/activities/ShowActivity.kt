package com.example.kotlinproject.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.example.kotlinproject.adapters.ViewPagerAdapter
import com.example.kotlinproject.common.BasisSingleton
import com.example.kotlinproject.common.GlobalVariables
import com.example.kotlinproject.service.Retrofit.Datas.PhotoInformations
import com.example.kotlinproject.service.Retrofit.Datas.PhotoSearch.PhotoSearch
import com.example.kotlinproject.service.Retrofit.Datas.PhotoSize.Images
import com.example.kotlinproject.service.Retrofit.Datas.PohotoInfo.PhotoInfo
import com.example.kotlinproject.R
import com.example.kotlinproject.service.Retrofit.Callback
import com.example.kotlinproject.service.Retrofit.RetrofitClient
import com.example.kotlinproject.service.Retrofit.WebAPI
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_show.*
import retrofit2.Call
import retrofit2.Response


class ShowActivity : AppCompatActivity(), OnMapReadyCallback {
    var photoInformations: ArrayList<PhotoInformations>? = null
    var photoList: ArrayList<PhotoInformations>? = null
    var imageUrls: ArrayList<String> = ArrayList<String>()
    lateinit var position: String
    lateinit var mMap: GoogleMap
    lateinit var SEARCH_TEXT: String
    var LATITUDE: Double = 0.0
    var LONGTITUDE: Double = 0.0
    var mCurrentPosition: Int = 0
    var PAGE: Int = 0
    private val retrofit = RetrofitClient.instance
    private val api = retrofit.create(WebAPI::class.java)
    private lateinit var viewPager: ViewPager
    private lateinit var viewPagerAdapter: ViewPagerAdapter


    override fun onMapReady(p0: GoogleMap?) {
        mMap = p0!!
        val location = LatLng(LATITUDE, LONGTITUDE)
        mMap.addMarker(MarkerOptions().position(location).title("Marker in Location where photo has taken"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 10f))
        setViewPager()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show)
        BasisSingleton.instance?.init(this@ShowActivity)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        init()
    }

    private fun init() {
        photoInformations = intent.getParcelableArrayListExtra<PhotoInformations>("listData")
        position = intent.getStringExtra("position")

        photoList = photoInformations
        LATITUDE = photoInformations!![position.toInt()].latitude?.toDouble()!!
        LONGTITUDE = photoInformations!![position.toInt()].longitude?.toDouble()!!
        SEARCH_TEXT = photoInformations!![0].searchText

        var btn_back = findViewById<Button>(R.id.btnBack)
        var tvTitle = findViewById<TextView>(R.id.tvFavoriId)
        tvTitle.setText(photoInformations!![position.toInt()].title)

        btn_back.setOnClickListener {
            onBackPressed()
        }

    }

    private fun setViewPager() {
        fillImageList()
        viewPager = findViewById(R.id.viewPager)
        viewPagerAdapter = ViewPagerAdapter(this@ShowActivity, imageUrls!!, mMap, tvFavoriId, photoList!!)
        viewPager.setAdapter(viewPagerAdapter)
        viewPager.setCurrentItem(position.toInt())
        var i = 0
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) {
            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
            }

            override fun onPageSelected(p0: Int) {
                mCurrentPosition = p0
                if (mCurrentPosition == viewPagerAdapter.count - 2) {
                    PAGE = (photoList!!.size / GlobalVariables.PER_PAGE.toInt()) + 1
                    searchPhoto(SEARCH_TEXT, PAGE.toString())
                    Log.d("Seçile i----->", "" + i)
                    i++
                    fillImageList()
                }
                Log.d("Seçilen Pencere pos", "" + p0)
            }
        })
    }

    private fun fillImageList() {
        imageUrls.clear()
        for (i in photoList!!) {
            imageUrls.add(i.imgUrlHD.toString())
        }
    }

    private fun searchPhoto(text: String, page: String) {
        val call = api.getPhotoSearch(
            GlobalVariables.METHOD_PHOTO_SEARCH,
            GlobalVariables.API_KEY,
            text,
            GlobalVariables.HAS_GEO,
            GlobalVariables.PER_PAGE,
            page,
            GlobalVariables.FORMAT_JSON,
            GlobalVariables.NOJSONCALLBACK
        )
        call.enqueue(object : Callback<PhotoSearch>() {
            override fun onResponse(call: Call<PhotoSearch>, response: Response<PhotoSearch>) {
                if (!response.isSuccessful) {
                    BasisSingleton.instance!!.toastShort("isNotSuccessful")
                } else {
                    val photos = listOf<PhotoSearch>(response.body()!!)
                    var size: Int
                    var photo_id: String
                    for (i in photos.indices) {
                        size = photos[i].photos?.photo?.size!!
                        for (j in 0..size - 1) {
                            photo_id = photos[i].photos?.photo!![j]?.id!!
                            photoSizes(photo_id)
                        }
                    }
                }
            }
        })
    }

    private fun photoSizes(photo_id: String) {
        val call = api.getImages(
            GlobalVariables.METHOD_PHOTO_SIZE,
            GlobalVariables.API_KEY,
            photo_id,
            GlobalVariables.FORMAT_JSON,
            GlobalVariables.NOJSONCALLBACK
        )
        call.enqueue(object : Callback<Images>() {
            override fun onResponse(call: Call<Images>, response: Response<Images>) {
                var imgUrl: String = ""
                var imgUrlHD: String = ""
                if (!response.isSuccessful) {
                    BasisSingleton.instance!!.toastShort("isNotSuccessful")
                } else {
                    var images = listOf<Images>(response.body()!!)
                    for (i in 0..images[0].sizes!!.size!!.size - 1) {
                        if (images[0].sizes?.size!![i]?.label.equals("Thumbnail")) {
                            imgUrl = images[0].sizes?.size?.get(i)?.source.toString()
                        }
                        if (images[0].sizes?.size!![i]?.label.equals("Medium 800")) {
                            imgUrlHD = images[0].sizes?.size?.get(i)?.source.toString()
                        }
                    }
                    photoInformation(photo_id, imgUrl, imgUrlHD)
                }
            }
        })
    }

    private fun photoInformation(photo_id: String, imgUrl: String, imgUrlHD: String) {
        val call = api.getPhotoInfo(
            GlobalVariables.METHOD_PHOTO_INFO,
            GlobalVariables.API_KEY,
            photo_id,
            GlobalVariables.FORMAT_JSON,
            GlobalVariables.NOJSONCALLBACK
        )
        call.enqueue(object : Callback<PhotoInfo>() {
            override fun onResponse(call: Call<PhotoInfo>, response: Response<PhotoInfo>) {
                if (!response.isSuccessful) {
                    BasisSingleton.instance?.toastShort("isNotSuccessful")
                } else {
                    var photo = listOf<PhotoInfo>(response.body()!!)
                    for (p in photo) {
                        photoList?.add(
                            PhotoInformations(
                                photo_id, SEARCH_TEXT, p.photo?.title?.content,
                                p.photo?.description?.content, p.photo?.owner?.realname, imgUrl, imgUrlHD,
                                p.photo?.location?.latitude, p.photo?.location?.longitude
                            )
                        )
                    }
                    viewPagerAdapter.setPhotoList(photoList)
                    viewPagerAdapter.notifyDataSetChanged()

                }
            }
        })
    }

}


