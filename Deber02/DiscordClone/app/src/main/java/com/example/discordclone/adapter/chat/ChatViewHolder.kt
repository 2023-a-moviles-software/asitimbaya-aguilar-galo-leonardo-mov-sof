package com.example.discordclone.adapter.chat

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.discordclone.R
import com.example.discordclone.model.Chat

class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val title: TextView = itemView.findViewById<TextView>(R.id.userName)
    private val description: TextView = itemView.findViewById<TextView>(R.id.userChat)

    fun bind(chat: Chat) {
        title.text = chat.title
        description.text = chat.description
    }
}