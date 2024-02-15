package com.example.interview1

import android.os.Parcel
import android.os.Parcelable

data class AuthorModel(val title:String, val subTitle:String, val imageView:String, val owner: Int) : Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt() ?: 0
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(subTitle)
        parcel.writeString(imageView)
        parcel.writeInt(owner)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AuthorModel> {
        override fun createFromParcel(parcel: Parcel): AuthorModel {
            return AuthorModel(parcel)
        }

        override fun newArray(size: Int): Array<AuthorModel?> {
            return arrayOfNulls(size)
        }
    }


}