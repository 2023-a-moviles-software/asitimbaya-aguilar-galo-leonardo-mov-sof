package com.example.autoassist.activity.authentication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.autoassist.R
import com.example.autoassist.activity.RegistrarVehiculoActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    private lateinit var editTextEmail: TextInputEditText
    private lateinit var editTextPassword: TextInputEditText
    private lateinit var textViewLoginHere: TextView
    private lateinit var buttonRegister: Button

    private lateinit var fireAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        initComponent()

        buttonRegister.setOnClickListener { view ->
            createUser()
        }

        navigateToLogin()
    }

    private fun initComponent() {
        editTextEmail = findViewById(R.id.etRegEmail)
        editTextPassword = findViewById(R.id.etRegPass)
        textViewLoginHere = findViewById(R.id.tvLoginHere)
        buttonRegister = findViewById(R.id.btnRegister)

        fireAuth = FirebaseAuth.getInstance()
    }

    private fun createUser() {
        val email = editTextEmail.text.toString()
        val password = editTextPassword.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(
                this,
                "Por favor, ingrese los datos respectivos",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            fireAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Usuario registrado con exito!", Toast.LENGTH_SHORT)
                            .show()
                        navigateToRegistrarVehiculo()
                    } else {
                        Toast.makeText(this, "El usuario no se pudo registrar", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
        }
    }

    private fun navigateToLogin() {
        textViewLoginHere.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun navigateToRegistrarVehiculo() {
        val intent = Intent(this, RegistrarVehiculoActivity::class.java)
        startActivity(intent)
    }

}