package com.example.letschat.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.letschat.databinding.FriendsListItemLayoytBinding
import com.example.letschat.model.User


class UserListAdapter(
    private val context: Context,
    private val userList: List<User>,
    private val onClickOnName: (data:User) -> Unit
) : RecyclerView.Adapter<UserListAdapter.UserViewHolder>() {

    inner class UserViewHolder(val binding: FriendsListItemLayoytBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: User) {
            binding.tvUserName.text = data.name
            binding.tvUserName.setOnClickListener {
                onClickOnName(data)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserViewHolder {
        return UserViewHolder(
            FriendsListItemLayoytBinding.inflate(LayoutInflater.from(context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(userList[position])
    }
}