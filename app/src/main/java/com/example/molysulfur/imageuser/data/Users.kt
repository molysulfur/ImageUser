package com.example.molysulfur.imageuser.data

import android.os.Parcel
import android.os.Parcelable
import com.example.molysulfur.imageuser.KParcelable
import com.google.gson.annotations.SerializedName

data class Users(
    @SerializedName("data") val data : MutableList<User>
) : KParcelable {
    constructor(parcel: Parcel) : this(
       mutableListOf<User>().apply {
           parcel.readTypedList(this,User.CREATOR)
       }
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeTypedList(data)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Users> {
        override fun createFromParcel(parcel: Parcel): Users {
            return Users(parcel)
        }

        override fun newArray(size: Int): Array<Users?> {
            return arrayOfNulls(size)
        }
    }
}