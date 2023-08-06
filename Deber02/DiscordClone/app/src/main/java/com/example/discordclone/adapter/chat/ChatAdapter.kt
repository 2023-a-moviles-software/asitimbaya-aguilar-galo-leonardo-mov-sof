package com.example.discordclone.adapter.chat

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.discordclone.R
import com.example.discordclone.model.Chat

class ChatAdapter(
    private var chats: List<Chat>,
    private val context: Context
) : RecyclerView.Adapter<ChatViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_chat, parent, false)
        return ChatViewHolder(itemView)
    }

    override fun getItemCount() = chats.size

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val currentChat = chats[position]
        holder.bind(currentChat)
    }

}