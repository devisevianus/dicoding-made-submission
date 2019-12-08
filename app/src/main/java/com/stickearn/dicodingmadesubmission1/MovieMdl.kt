package com.stickearn.dicodingmadesubmission1

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by devis on 2019-12-08
 */

data class MovieMdl(
    val position: Int?,
    val title: String?,
    val date: String?,
    val rating: String?,
    val director: String?,
    val overview: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(position)
        parcel.writeString(title)
        parcel.writeString(date)
        parcel.writeString(rating)
        parcel.writeString(director)
        parcel.writeString(overview)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MovieMdl> {
        override fun createFromParcel(parcel: Parcel): MovieMdl {
            return MovieMdl(parcel)
        }

        override fun newArray(size: Int): Array<MovieMdl?> {
            return arrayOfNulls(size)
        }
    }

}