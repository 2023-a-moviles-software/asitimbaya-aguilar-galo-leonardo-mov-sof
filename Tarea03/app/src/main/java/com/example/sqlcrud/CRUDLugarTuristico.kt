package com.example.sqlcrud

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import kotlin.math.cos

class CRUDLugarTuristico : AppCompatActivity() {
    val formatoFecha = SimpleDateFormat("dd/MM/yyyy")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crudlt)

        val idLugarAEditar = intent.getIntExtra("idLugar", 0)
        val idPaisSelected = intent.getIntExtra("id", 0)
        llenarDatosFormulario(idLugarAEditar)


        val botonCrearBDD = findViewById<Button>(R.id.btn_createLugar)
        botonCrearBDD
            .setOnClickListener {
                try {
                    val nombreLugar = findViewById<EditText>(R.id.input_nombreLugar)
                    val costoEntrada = findViewById<EditText>(R.id.input_costoLugar)
                    val fechaCreacion = findViewById<EditText>(R.id.input_fechaLugar)
                    val disponible: Boolean = findViewById<Switch>(R.id.sw_disponible).isChecked
                    val idPais = idPaisSelected
                    BaseDeDatos.tablaLugarTuristico!!.crearLugar(
                        nombreLugar.text.toString(),
                        costoEntrada.text.toString().toDouble(),
                        fechaCreacion.text.toString(),
                        disponible,
                        idPais
                    )
                    finish()
                } catch (e: Exception) {
                    mostrarSnackBar("Error, datos incorrectos")
                }
            }

        val botonActualizarBDD = findViewById<Button>(R.id.btn_updateLugar)
        botonActualizarBDD
            .setOnClickListener {
                try {
                    val id = idLugarAEditar
                    val nombreLugar = findViewById<EditText>(R.id.input_nombreLugar)
                    val costoEntrada = findViewById<EditText>(R.id.input_costoLugar)
                    val fechaCreacion = findViewById<EditText>(R.id.input_fechaLugar)
                    val disponible: Boolean = findViewById<Switch>(R.id.sw_disponible).isChecked
                    val idPais = idPaisSelected
                    BaseDeDatos.tablaLugarTuristico!!.actualizarLugarFormulario(
                        nombreLugar.text.toString(),
                        costoEntrada.text.toString().toDouble(),
                        fechaCreacion.text.toString(),
                        disponible,
                        idPais,
                        id
                    )
                    finish()
                } catch (e: Exception) {
                    mostrarSnackBar("Error, datos incorrectos")
                }
            }
        if (idLugarAEditar !=0){
            //ocultar boton crear
            botonCrearBDD
                .visibility = Button.INVISIBLE
        }else{
            //ocultar boton actualizar
            botonActualizarBDD
                .visibility = Button.INVISIBLE
        }
    }

    fun llenarDatosFormulario(idLugarAEditar: Int) {
        if (idLugarAEditar != 0) {
            val lugarEncontrado =
                BaseDeDatos.tablaLugarTuristico!!.consultarLugarPorID(idLugarAEditar)
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
    }

    fun mostrarSnackBar(texto: String) {
        Snackbar.make(
            findViewById(R.id.cl_crudLT),
            texto,
            Snackbar.LENGTH_LONG
        )
            .setAction("Action", null).show()
    }
}