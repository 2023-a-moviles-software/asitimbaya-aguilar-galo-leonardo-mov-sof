package com.example.autoassist.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.widget.EditText
import android.widget.Switch
import com.example.autoassist.R
import com.example.autoassist.model.Mantenimiento
import com.google.firebase.firestore.FirebaseFirestore

class RegistroActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        val email = intent.getStringExtra("email") ?: ""
        val idMantenimiento = intent.getStringExtra("idMantenimiento") ?: ""


    }




}
