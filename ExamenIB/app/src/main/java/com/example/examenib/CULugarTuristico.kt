package com.example.examenib

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat

class CULugarTuristico : AppCompatActivity() {
    val arregloLugarTuristico = BaseDatosMemoria.arregloLugarTuristico

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_culugar_turistico)

        val formatoFecha = SimpleDateFormat("dd/MM/yyyy")

        val btnCULugarTuristico = findViewById<Button>(R.id.btn_CULugar)
        val idLugarAEditar = intent.getIntExtra("idLugar", 0)
        val idPaisSelected = intent.getIntExtra("id", 0)

        findViewById<TextView>(R.id.tv_CULugar).text =
            if (idLugarAEditar == 0) "Crear Lugar" else "Editar Lugar"

        btnCULugarTuristico.text = if (idLugarAEditar == 0) "Crear" else "Actualizar"

        if (idLugarAEditar != 0) {
            val lugarEncontrado = BaseDatosMemoria.arregloLugarTuristico
                .find {
                    it.idLT == idLugarAEditar
                }
            if (lugarEncontrado != null) {
                findViewById<EditText>(R.id.input_nombreLugar).setText(lugarEncontrado.nombreLT)
                findViewById<EditText>(R.id.input_costoLugar).setText(lugarEncontrado.costoEntrada.toString())

                findViewById<EditText>(R.id.input_fechaLugar).setText(
                    formatoFecha.format(
                        lugarEncontrado.fechaCreacion
                    )
                )
                findViewById<Switch>(R.id.sw_disponible).isChecked = lugarEncontrado.disponible!!
            }
        }
        btnCULugarTuristico.setOnClickListener {
            try {
                val nombre = findViewById<EditText>(R.id.input_nombreLugar)
                val costo = findViewById<EditText>(R.id.input_costoLugar)
                val fecha = findViewById<EditText>(R.id.input_fechaLugar)
                val disponible: Boolean = findViewById<Switch>(R.id.sw_disponible).isChecked


                //Eliminar lugar de la lista si es que existe y crear uno nuevo

                val lugarEncontrado = arregloLugarTuristico
                    .find {
                        it.idLT == idLugarAEditar
                    }
                if (lugarEncontrado != null) {
                    arregloLugarTuristico.remove(lugarEncontrado)
                }
                arregloLugarTuristico.add(
                    LugarTuristico(
                        arregloLugarTuristico.size + 2,
                        nombre.text.toString(),
                        costo.text.toString().toDouble(),
                        formatoFecha.parse(fecha.text.toString()),
                        disponible,
                        idPaisSelected
                    )
                )
                finish()
            } catch (e: Exception) {
                mostrarSnackBar("Error, datos incorrectos")
            }

        }
    }


    fun mostrarSnackBar(texto: String) {
        Snackbar.make(
            findViewById(R.id.cl_CULugar),
            texto,
            Snackbar.LENGTH_LONG
        )
            .setAction("Action", null).show()
    }
}