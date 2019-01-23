package com.example.molysulfur.imageuser.adapter.item

import com.example.molysulfur.imageuser.adapter.creator.UserCreator
import com.example.molysulfur.imageuser.data.User

class UserItem(user: User) : BaseItem(UserCreator.TYPE_USER_LIST){
    val name : String? = user.name
    val url : String? = user.url
}