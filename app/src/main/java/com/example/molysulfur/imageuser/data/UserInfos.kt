package com.example.molysulfur.imageuser.data

import android.os.Parcel
import android.os.Parcelable
import com.example.molysulfur.imageuser.KParcelable
import com.google.gson.annotations.SerializedName

data class UserInfos(
    @SerializedName("data") val data : MutableList<UserInfo>
) : KParcelable {
    constructor(parcel: Parcel) : this(
        mutableListOf<UserInfo>().apply {
            parcel.readTypedList(this,UserInfo.CREATOR)
        }
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeTypedList(data)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserInfos> {
        override fun createFromParcel(parcel: Parcel): UserInfos {
            return UserInfos(parcel)
        }

        override fun newArray(size: Int): Array<UserInfos?> {
            return arrayOfNulls(size)
        }
    }
}