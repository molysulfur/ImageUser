package com.example.molysulfur.imageuser.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.molysulfur.imageuser.R
import com.example.molysulfur.imageuser.adapter.creator.UserCreator.Companion.TYPE_USERINFO_LIST
import com.example.molysulfur.imageuser.adapter.creator.UserCreator.Companion.TYPE_USER_LIST
import com.example.molysulfur.imageuser.adapter.holder.UserHolder
import com.example.molysulfur.imageuser.adapter.holder.UserInfoHolder
import com.example.molysulfur.imageuser.adapter.item.BaseItem
import com.example.molysulfur.imageuser.adapter.item.UserInfoItem
import com.example.molysulfur.imageuser.adapter.item.UserItem

class UserListAdapter(val listUser : List<BaseItem>?,val selectorListener: SelectorListener?) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private val selectorCallback = object : UserListAdapter.SelectorListener{
        override fun onCurrentImageChange(url: String, callback: SelectorListener?) {
            listUser?.forEach {
                (it as UserInfoItem).current = it.url == url
            }
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, type: Int): RecyclerView.ViewHolder {
        return when(type) {
            TYPE_USER_LIST -> {
                val rootView = LayoutInflater.from(viewGroup.context).inflate(R.layout.layout_user_list, viewGroup, false)
                UserHolder(rootView)
            }
            TYPE_USERINFO_LIST ->{
                val rootView = LayoutInflater.from(viewGroup.context).inflate(R.layout.layout_thumbnail, viewGroup, false)
                UserInfoHolder(rootView,selectorCallback)
            }
            else -> {
                super.createViewHolder(viewGroup,type)
            }
        }
    }

    override fun getItemViewType(position: Int): Int = listUser?.get(position)?.type?:0

    override fun getItemCount(): Int = listUser?.size?:0

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when {
            listUser?.get(position)?.type == TYPE_USER_LIST -> {
                viewHolder as UserHolder
                viewHolder.onBind(listUser[position] as UserItem)
            }
            listUser?.get(position)?.type == TYPE_USERINFO_LIST -> {
                if (selectorListener != null) {
                    viewHolder as UserInfoHolder
                    viewHolder.onCurrentChange(selectorListener)
                    viewHolder.onBind(listUser[position] as UserInfoItem)
                }
            }

        }
    }

    interface SelectorListener{
        fun onCurrentImageChange(url : String,callback : SelectorListener?)
    }
}