package com.example.autoassist.activity.authentication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.autoassist.R
import com.example.autoassist.activity.InicioActivity
import com.example.autoassist.activity.RegistrarVehiculoActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var editTextLoginEmail: TextInputEditText
    private lateinit var editTextLoginPassword: TextInputEditText
    private lateinit var textViewRegisterHere: TextView
    private lateinit var buttonLogin: Button

    private lateinit var fireAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initComponent()

        buttonLogin.setOnClickListener {
            loginUser()
        }

        navigateToRegister()
    }

    private fun initComponent() {
        editTextLoginEmail = findViewById(R.id.etLoginEmail)
        editTextLoginPassword = findViewById(R.id.etLoginPass)
        textViewRegisterHere = findViewById(R.id.tvRegisterHere)
        buttonLogin = findViewById(R.id.btnLogin)

        fireAuth = FirebaseAuth.getInstance()
    }

    private fun loginUser() {
        val email = editTextLoginEmail.text.toString()
        val password = editTextLoginPassword.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(
                this,
                "Por favor, ingrese los datos respectivos",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            fireAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            this,
                            "El usuario a ingresado con exito!",
                            Toast.LENGTH_SHORT
                        ).show()
                        abrirActividadConParametros(InicioActivity::class.java, email)
                    } else {
                        Toast.makeText(
                            this, "El usuario no se pudo ingresar",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }

    private fun navigateToRegister() {
        textViewRegisterHere.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    fun abrirActividadConParametros(clase: Class<*>, correo: String) {
        val intentExplicito = Intent(this, clase)
        // Enviar parámetros
        intentExplicito.putExtra("email", correo)
        // Iniciar la actividad
        startActivity(intentExplicito)
    }
}