package com.example.molysulfur.imageuser.adapter.creator

import com.example.molysulfur.imageuser.data.UserInfo
import com.example.molysulfur.imageuser.adapter.item.BaseItem
import com.example.molysulfur.imageuser.adapter.item.UserInfoItem
import com.example.molysulfur.imageuser.adapter.item.UserItem
import com.example.molysulfur.imageuser.data.User

class UserCreator{
    companion object {
        const val TYPE_USER_LIST = 0
        const val TYPE_USERINFO_LIST = 1

        fun toUsersBaseItem(users: MutableList<User>?): List<BaseItem> {
            val listItem = ArrayList<BaseItem>()
            users?.forEach {
                listItem.add(UserItem(it))
            }
            return listItem
        }

        fun toUserInfoBaseItem(userInfo : List<UserInfo>?): List<BaseItem> {
            val listItem = ArrayList<BaseItem>()
            if (userInfo != null) {
                for (index in 0..userInfo.size-1) {
                    when(index) {
                        0 -> {
                            val info = UserInfoItem(userInfo[index])
                            info.current = true
                            listItem.add(info)
                        }
                        else ->{
                            listItem.add(UserInfoItem(userInfo[index]))
                        }
                    }
                }
            }
            return listItem
        }
    }
}