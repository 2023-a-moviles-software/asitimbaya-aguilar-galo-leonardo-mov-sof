package com.example.autoassist

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.autoassist.activity.authentication.LoginActivity

class MainActivity : AppCompatActivity() {
    private lateinit var buttonEmpezar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initComponent()
        navigateToLogin()
    }

    private fun initComponent() {
        buttonEmpezar = findViewById<Button>(R.id.btn_main)
    }

    private fun navigateToLogin() {
        buttonEmpezar.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}