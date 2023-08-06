package com.example.discordclone.adapter.friends

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.discordclone.R
import com.example.discordclone.adapter.chat.ChatViewHolder
import com.example.discordclone.model.Friends

class FriendsAdapter(
    private var friends: List<Friends>,
    private val context: Context
) : RecyclerView.Adapter<FriendsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_chat, parent, false)
        return FriendsViewHolder(itemView)
    }

    override fun getItemCount() = friends.size

    override fun onBindViewHolder(holder: FriendsViewHolder, position: Int) {
        val currentFriend = friends[position]
        holder.bind(currentFriend)
    }
}