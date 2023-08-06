package com.example.discordclone

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.discordclone.activity.ServerActivity
import com.example.discordclone.adapter.chat.ChatAdapter
import com.example.discordclone.data.DataDiscord

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var chatAdapter: ChatAdapter

    private lateinit var data: DataDiscord

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecyclerView()
        navigateToServer()
    }

    private fun initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerViewChat)
        recyclerView.layoutManager = LinearLayoutManager(this)

        data = DataDiscord()

        chatAdapter = ChatAdapter(data.insertDataOfChat(), this)
        recyclerView.adapter = chatAdapter
    }

    private fun navigateToServer() {
        val serverLayout = findViewById<ImageView>(R.id.serverButton)

        serverLayout.setOnClickListener {
            val intent = Intent(this, ServerActivity::class.java)
            startActivity(intent)
        }
    }
}