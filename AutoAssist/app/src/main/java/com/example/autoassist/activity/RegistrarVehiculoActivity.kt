package com.example.autoassist.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.autoassist.R
import com.example.autoassist.model.Vehiculo
import com.google.android.material.textfield.TextInputEditText

class RegistrarVehiculoActivity : AppCompatActivity() {
    private lateinit var editTextNombreVehiculo: TextInputEditText
    private lateinit var editTextImagenVehiculo: TextInputEditText
    private lateinit var buttonSaveRegistrarVehiculo: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_vehiculo)

        initComponent()

        buttonSaveRegistrarVehiculo.setOnClickListener {
            registrarVehiculo()
            navigateToInicio()
        }
    }

    private fun initComponent() {
        buttonSaveRegistrarVehiculo = findViewById(R.id.button_save_registrar_vehiculo)
        editTextNombreVehiculo = findViewById(R.id.etNombreVehiculo)
        editTextImagenVehiculo = findViewById(R.id.etImagenVehiculo)
    }

    private fun registrarVehiculo() {
        // Obtener valores de la intefaz
        val nombreVehiculo = editTextNombreVehiculo.text.toString()
        val imagenVehiculo = editTextImagenVehiculo.text.toString()

        // Comprobar si se ingreso valores
        if (
            nombreVehiculo.isEmpty() ||
            imagenVehiculo.isEmpty()
        ) {
            Toast.makeText(this, "Existen campos vacios!", Toast.LENGTH_SHORT).show()
        } else {
            // TODO: Implementar el guardado en la base de datos
            val vehiculo = Vehiculo(
                nombreVehiculo,
                imagenVehiculo
            )
        }
    }

    private fun navigateToInicio() {
        val intent = Intent(this, InicioActivity::class.java)
        startActivity(intent)
        finish()
    }
}