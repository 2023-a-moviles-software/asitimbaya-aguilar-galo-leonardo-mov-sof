package com.example.discordclone.adapter.server

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.discordclone.R
import com.example.discordclone.model.Server

class ServerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val serverImage: ImageView = itemView.findViewById(R.id.serverIcon)

    fun bind(server: Server) {
        serverImage.id = server.image
    }
}