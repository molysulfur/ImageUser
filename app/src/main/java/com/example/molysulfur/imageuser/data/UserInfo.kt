package com.example.molysulfur.imageuser.data

import android.os.Parcel
import android.os.Parcelable
import com.example.molysulfur.imageuser.KParcelable
import com.google.gson.annotations.SerializedName

data class UserInfo(
    @SerializedName("url") val url : String? ="",
    @SerializedName("thumbnail") val thumbnail : String? =""
) : KParcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(url)
        dest.writeString(thumbnail)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserInfo> {
        override fun createFromParcel(parcel: Parcel): UserInfo {
            return UserInfo(parcel)
        }

        override fun newArray(size: Int): Array<UserInfo?> {
            return arrayOfNulls(size)
        }
    }
}