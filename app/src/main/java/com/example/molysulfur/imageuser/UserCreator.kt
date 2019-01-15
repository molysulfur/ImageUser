package com.example.molysulfur.imageuser

import com.example.molysulfur.imageuser.data.User
import com.example.molysulfur.imageuser.data.UserInfo
import com.example.molysulfur.imageuser.item.BaseItem
import com.example.molysulfur.imageuser.item.UserInfoItem
import com.example.molysulfur.imageuser.item.UserItem

class UserCreator{
    companion object {
        const val TYPE_USER_LIST = 0
        const val TYPE_USERINFO_LIST = 1

        fun toUsersBaseItem(users : List<User>?): List<BaseItem> {
            val listItem = ArrayList<BaseItem>()
            users?.forEach {
                listItem.add(UserItem(it))
            }
            return listItem
        }

        fun toUserInfoBaseItem(userInfo : List<UserInfo>?): List<BaseItem> {
            val listItem = ArrayList<BaseItem>()
            userInfo?.forEach {
                listItem.add(UserInfoItem(it))
            }
            return listItem
        }
    }
}