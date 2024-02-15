package com.example.interview1.model

import android.os.Parcel
import android.os.Parcelable

data class Index(
    val authorid: Int, val cd_downloads: Int, val curriculum_tags: List<String>, val downloadid: Int, val educator: String,
    val id: Int, val owned: Int, val progress_tracking: Double, //val purchase_order: Int,
    val purchase_order: Any, val release_date: String, val sale: Int, val series_tags: List<String>,
    val skill_tags: List<String>, val status: Int, val style_tags: List<String>, val title: String, val video_count: Int, val watched: Int
)
    : Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.createStringArrayList() ?: listOf(),
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readInt(),
        parcel.readDouble(),
        Any(), // You need to properly handle this based on your use case
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.createStringArrayList() ?: listOf(),
        parcel.createStringArrayList() ?: listOf(),
        parcel.readInt(),
        parcel.createStringArrayList() ?: listOf(),
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(authorid)
        parcel.writeInt(cd_downloads)
        parcel.writeStringList(curriculum_tags)
        parcel.writeInt(downloadid)
        parcel.writeString(educator)
        parcel.writeInt(id)
        parcel.writeInt(owned)
        parcel.writeDouble(progress_tracking)
        // Handle parcelable for purchase_order based on your specific type
        // parcel.writeXYZ(purchase_order)
        parcel.writeString(release_date)
        parcel.writeInt(sale)
        parcel.writeStringList(series_tags)
        parcel.writeStringList(skill_tags)
        parcel.writeInt(status)
        parcel.writeStringList(style_tags)
        parcel.writeString(title)
        parcel.writeInt(video_count)
        parcel.writeInt(watched)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Index> {
        override fun createFromParcel(parcel: Parcel): Index {
            return Index(parcel)
        }

        override fun newArray(size: Int): Array<Index?> {
            return arrayOfNulls(size)
        }
    }

}