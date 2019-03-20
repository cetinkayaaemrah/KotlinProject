package com.example.kotlinproject.service.Retrofit.Datas

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class PhotoInformations(
    @field:SerializedName("photo_id")
    val photo_id: String = null ?: "",


    @field:SerializedName("search_text")
    val searchText: String = null ?: "",


    @field:SerializedName("title")
    val title: String? = null ?: "",

    @field:SerializedName("description")
    val description: String? = null ?: "",


    @field:SerializedName("realName")
    val realName: String? = null ?: "",

    @field:SerializedName("imgUrl")
    val imgUrl: String? = null ?: "",

    @field:SerializedName("imgUrlHD")
    val imgUrlHD: String? = null ?: "",

    @field:SerializedName("latitude")
    val latitude: String? = null ?: "",

    @field:SerializedName("longitude")
    val longitude: String? = null ?: ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(photo_id)
        parcel.writeString(title)
        parcel.writeString(searchText)
        parcel.writeString(description)
        parcel.writeString(realName)
        parcel.writeString(imgUrl)
        parcel.writeString(imgUrlHD)
        parcel.writeString(latitude)
        parcel.writeString(longitude)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PhotoInformations> {
        override fun createFromParcel(parcel: Parcel): PhotoInformations {
            return PhotoInformations(parcel)
        }

        override fun newArray(size: Int): Array<PhotoInformations?> {
            return arrayOfNulls(size)
        }
    }
}
