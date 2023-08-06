package com.example.discordclone.activity

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.discordclone.MainActivity
import com.example.discordclone.R
import com.example.discordclone.adapter.server.ServerAdapter
import com.example.discordclone.data.DataDiscord

class ServerActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var serverAdapter: ServerAdapter

    private lateinit var data: DataDiscord

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_server)

        initRecyclerView()
        navigateToMain()
    }

    private fun initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerviewServer)
        recyclerView.layoutManager = LinearLayoutManager(this)

        data = DataDiscord()

        serverAdapter = ServerAdapter(data.insertDataOfServer(), this)
        recyclerView.adapter = serverAdapter
    }

    private fun navigateToMain(){
        val returnLayout = findViewById<ImageView>(R.id.returnIcon)

        returnLayout.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

}