package com.example.molysulfur.imageuser.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.molysulfur.imageuser.R
import com.example.molysulfur.imageuser.holder.UserHolder
import com.example.molysulfur.imageuser.item.BaseItem
import com.example.molysulfur.imageuser.item.UserItem

class UserListAdapter(val listUser : List<BaseItem>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, type: Int): RecyclerView.ViewHolder {
        val rootView = LayoutInflater.from(viewGroup.context).inflate(R.layout.layout_user_list,viewGroup,false)
        return UserHolder(rootView)
    }

    override fun getItemViewType(position: Int): Int = listUser?.get(position)?.type?:0

    override fun getItemCount(): Int = listUser?.size?:0

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        viewHolder as UserHolder
        viewHolder.onBind(listUser?.get(position) as UserItem)
    }
}