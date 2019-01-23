package com.example.molysulfur.imageuser.adapter.item

import com.example.molysulfur.imageuser.adapter.creator.UserCreator
import com.example.molysulfur.imageuser.data.UserInfo

class UserInfoItem(userInfo: UserInfo) : BaseItem(UserCreator.TYPE_USERINFO_LIST){
    val thumbnail : String? = userInfo.thumbnail
    val url : String? = userInfo.url
    var current : Boolean = false
}