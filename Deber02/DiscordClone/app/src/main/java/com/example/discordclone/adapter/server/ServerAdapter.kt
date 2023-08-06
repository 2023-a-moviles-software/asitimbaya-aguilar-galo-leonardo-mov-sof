package com.example.discordclone.adapter.server

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.discordclone.R
import com.example.discordclone.model.Server

class ServerAdapter(
    private var servers: List<Server>,
    private val context: Context
) : RecyclerView.Adapter<ServerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServerViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_server, parent, false)
        return ServerViewHolder(itemView)
    }

    override fun getItemCount() = servers.size

    override fun onBindViewHolder(holder: ServerViewHolder, position: Int) {
        val currentServer = servers[position]
        holder.bind(currentServer)
    }
}