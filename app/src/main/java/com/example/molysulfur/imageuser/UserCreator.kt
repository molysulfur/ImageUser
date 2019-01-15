package com.example.molysulfur.imageuser

import com.example.molysulfur.imageuser.data.User
import com.example.molysulfur.imageuser.item.BaseItem
import com.example.molysulfur.imageuser.item.UserItem

class UserCreator{
    companion object {
        const val TYPE_USER_LIST = 0

        fun toBaseItem(users : List<User>?): List<BaseItem> {
            val listItem = ArrayList<BaseItem>()
            users?.forEach {
                listItem.add(UserItem(it))
            }
            return listItem
        }
    }
}