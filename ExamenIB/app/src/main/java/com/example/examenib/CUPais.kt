package com.example.examenib

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar

class CUPais : AppCompatActivity() {
    val arregloPais = BaseDatosMemoria.arregloPais


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cupais)

        val botonCrearPais = findViewById<Button>(R.id.btn_crearActualizar)

        val idPaisAEditar = intent.getIntExtra("id", 0)

        findViewById<TextView>(R.id.tv_CUPais).text =
            if (idPaisAEditar == 0) "Crear Pais" else "Editar Pais"

        botonCrearPais.text = if (idPaisAEditar == 0) "Crear" else "Actualizar"

        if (idPaisAEditar != 0) {
            val paisEncontrado = arregloPais
                .find {
                    it.id == idPaisAEditar
                }
            if (paisEncontrado != null) {
                findViewById<EditText>(R.id.input_nombrePais).setText(paisEncontrado.nombre)
                findViewById<EditText>(R.id.input_idiomaPais).setText(paisEncontrado.idioma)
                findViewById<EditText>(R.id.input_monedaPais).setText(paisEncontrado.moneda)
                findViewById<EditText>(R.id.input_precioDolar).setText(paisEncontrado.precioDolar.toString())
            }
        }

        botonCrearPais.setOnClickListener {
            try {
                val nombre = findViewById<EditText>(R.id.input_nombrePais)
                val idioma = findViewById<EditText>(R.id.input_idiomaPais)
                val moneda = findViewById<EditText>(R.id.input_monedaPais)
                val precioDolar = findViewById<EditText>(R.id.input_precioDolar)

                //Eliminar pais de la lista si es que existe y crear uno nuevo

                val paisEncontrado = arregloPais
                    .find {
                        it.id == idPaisAEditar
                    }

                if (paisEncontrado != null) {
                    arregloPais.remove(paisEncontrado)
                }

                arregloPais.add(
                    Pais(
                        arregloPais.size + 1,
                        nombre.text.toString(),
                        idioma.text.toString(),
                        moneda.text.toString(),
                        precioDolar.text.toString().toDouble()
                    )
                )

                finish()
            } catch (e: Exception) {
                mostrarSnackBar("Ups, parece que los datos son incorrectos")
            }

        }


    }

    fun mostrarSnackBar(texto: String) {
        Snackbar.make(
            findViewById(R.id.cl_CUPais),
            texto,
            Snackbar.LENGTH_LONG
        )
            .setAction("Action", null).show()
    }
}