package com.example.molysulfur.imageuser.data

import android.os.Parcel
import android.os.Parcelable
import com.example.molysulfur.imageuser.KParcelable
import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("name") val name : String? = "",
    @SerializedName("url") val url : String? = ""
) : KParcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(name)
        dest.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}