package com.example.molysulfur.imageuser.item

import com.example.molysulfur.imageuser.UserCreator
import com.example.molysulfur.imageuser.data.UserInfo

class UserInfoItem(userInfo: UserInfo) : BaseItem(UserCreator.TYPE_USERINFO_LIST){
    val thumbnail : String? = userInfo.thumbnail
    val url : String? = userInfo.url
    var current : Boolean = false
}