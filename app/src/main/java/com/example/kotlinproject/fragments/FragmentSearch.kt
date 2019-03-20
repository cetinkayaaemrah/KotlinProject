package com.example.kotlinproject.fragments


import android.app.Activity
import android.app.Dialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.kotlinproject.adapters.RecyclerviewAdapterSearch
import com.example.kotlinproject.adapters.SpinnerCustomAdapter
import com.example.kotlinproject.common.BasisSingleton
import com.example.kotlinproject.common.GlobalVariables
import com.example.kotlinproject.database.database.ViewModel
import com.example.kotlinproject.service.Retrofit.Callback
import com.example.kotlinproject.service.Retrofit.Datas.PhotoInformations
import com.example.kotlinproject.service.Retrofit.Datas.PhotoSearch.PhotoSearch
import com.example.kotlinproject.service.Retrofit.Datas.PhotoSize.Images
import com.example.kotlinproject.service.Retrofit.Datas.PohotoInfo.PhotoInfo
import com.example.kotlinproject.service.Retrofit.RetrofitClient
import com.example.kotlinproject.service.Retrofit.WebAPI
import com.example.kotlinproject.activities.Main2Activity
import com.example.kotlinproject.database.database.Favorite
import kotlinx.android.synthetic.main.fragment_search.*
import retrofit2.Call
import retrofit2.Response
import java.util.ArrayList
import android.widget.Toast
import com.example.kotlinproject.R
import com.example.kotlinproject.database.database.Category


class FragmentSearch : Fragment() {
    private lateinit var mView: View
    private lateinit var mAdapterSearch: RecyclerviewAdapterSearch
    private lateinit var SEARCHTEXT: String
    private var photoInformation = ArrayList<PhotoInformations>()
    private val retrofit = RetrofitClient.instance
    private val api = retrofit.create(WebAPI::class.java)
    private lateinit var mViewModel: ViewModel
    private var PAGE: Int = 0
    var favorites = emptyList<Favorite>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_search, container, false)
        BasisSingleton.instance!!.init(context!!)
        mViewModel = ViewModelProviders.of(context as Main2Activity).get(ViewModel::class.java)

        mViewModel.allFavorites.observe(context as Main2Activity, Observer { favorite ->
            favorite?.let { favorites = it
                init()
            }
        })

        return mView
    }

    private fun init() {
        val btn_popupSearch = mView.findViewById<Button>(R.id.btn_popup_search)
        btn_popupSearch?.setOnClickListener {
            popUp()
        }
    }

    fun popUpLike(context: Context, position: Int, photoList: ArrayList<PhotoInformations>, ibtnLike: ImageButton,categoryList: List<Category>) {
        lateinit var mViewModel: ViewModel
        var mDialogFavorite: Dialog = Dialog(context)
        mDialogFavorite.setContentView(R.layout.popup_favorite_layout)
        val btnAddFavorite = mDialogFavorite.findViewById<Button>(R.id.btnAddFavorite)
        var spinner = mDialogFavorite.findViewById<Spinner>(R.id.spinerCategories)
        var spinnerAdapter = SpinnerCustomAdapter(categoryList, context as Activity)
        spinner.adapter = spinnerAdapter

        spinnerAdapter.setCategories(categoryList)
        spinnerAdapter.notifyDataSetChanged()

        btnAddFavorite.setOnClickListener {
            if (spinner.selectedItemPosition == 0) {
                Toast.makeText(context, "Select a category", Toast.LENGTH_SHORT).show()
            } else {
                mViewModel = ViewModelProviders.of(context as Main2Activity).get(ViewModel::class.java)
                mViewModel.insertFavorite(
                    Favorite(
                        0,
                        categoryList[spinner.selectedItemPosition].category_id,
                        photoList[position].photo_id,
                        photoList[position].title,
                        photoList[position].description,
                        photoList[position].imgUrl,
                        photoList[position].imgUrlHD,
                        photoList[position].latitude,
                        photoList[position].longitude
                    )
                )
                mDialogFavorite.dismiss()
                Toast.makeText(context, "Added Favorite.", Toast.LENGTH_SHORT).show()
                ibtnLike.setImageResource(R.drawable.ic_like)
            }


        }
        mDialogFavorite.show()
    }

    private fun popUp() {
        var mDialogSearch = Dialog(context)
        mDialogSearch.setContentView(R.layout.popup_search_layout)
        val etSearch = mDialogSearch.findViewById<EditText>(R.id.et_search)
        val btnDialogSearch = mDialogSearch.findViewById<Button>(R.id.btn_search)

        btnDialogSearch.setOnClickListener {
            PAGE = 1
            photoInformation.clear()
            SEARCHTEXT = etSearch.text.toString()
            searchPhoto(SEARCHTEXT, GlobalVariables.DEFAULT_PAGE)
            mDialogSearch.dismiss()
            photoRecyclerView()
        }
        mDialogSearch.show()
    }

    private fun photoRecyclerView() {
        recycler_view.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false) // thiss -- context
        recycler_view.setHasFixedSize(true)

        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    prgBarSearch.visibility = View.VISIBLE
                    searchPhoto(SEARCHTEXT, PAGE.toString())
                    PAGE++
                }
            }
        })
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
                    val photos = listOf(response.body()!!)
                    var size: Int
                    var photo_id: String
                    for (i in photos.indices) {
                        size = photos[i].photos?.photo?.size!!
                        for (j in 0 until size) {
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
                var imgUrl = ""
                var imgUrlHD = ""
                if (!response.isSuccessful) {
                    BasisSingleton.instance!!.toastShort("isNotSuccessful")
                } else {
                    var images = listOf(response.body()!!)
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
                    var photo = listOf(response.body()!!)
                    for (p in photo) {
                        photoInformation.add(
                            PhotoInformations(
                                photo_id, p.photo?.title?.content!!, SEARCHTEXT,
                                p.photo?.description?.content, p.photo?.owner?.realname, imgUrl, imgUrlHD,
                                p.photo?.location?.latitude, p.photo?.location?.longitude
                            )
                        )
                    }

                    mAdapterSearch = RecyclerviewAdapterSearch(
                        photoInformation,
                        context!!,
                        PAGE,
                        favorites
                    )
                    recycler_view.swapAdapter(mAdapterSearch, false)
                    prgBarSearch.visibility = View.GONE
                }
            }
        })
    }

}
