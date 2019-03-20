package com.example.kotlinproject.service.Retrofit

import com.example.kotlinproject.common.BasisSingleton
import retrofit2.Call
import retrofit2.Response

open class Callback<T> : retrofit2.Callback<T> {

    override fun onResponse(call: Call<T>, response: Response<T>) {

    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        BasisSingleton.instance!!.toastShort("OnFailure!")
        call.cancel()
    }
}
