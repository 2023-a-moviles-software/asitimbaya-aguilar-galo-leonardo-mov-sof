package com.example.sqlcrud

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class CRUDPais : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crudactivity)
        val idPais = intent.getIntExtra("id", 0)
        llenarDatosFormulario(idPais)

        val botonCrearBDD = findViewById<Button>(R.id.btn_crear_bdd)
        botonCrearBDD
            .setOnClickListener {
                val nombre = findViewById<EditText>(R.id.input_nombre_pais)
                val idioma = findViewById<EditText>(R.id.input_idioma)
                val moneda = findViewById<EditText>(R.id.input_moneda)
                val precioDolar = findViewById<EditText>(R.id.input_precioDolar)
                BaseDeDatos.tablaPais!!.crearPais(
                    nombre.text.toString(),
                    idioma.text.toString(),
                    moneda.text.toString(),
                    precioDolar.text.toString().toDouble()
                )
                finish()
            }


        val botonActualizarBDD = findViewById<Button>(R.id.btn_actualizar_bdd)
        botonActualizarBDD
            .setOnClickListener {
                val id =  idPais
                val nombre = findViewById<EditText>(R.id.input_nombre_pais)
                val idioma = findViewById<EditText>(R.id.input_idioma)
                val moneda = findViewById<EditText>(R.id.input_moneda)
                val precioDolar = findViewById<EditText>(R.id.input_precioDolar)
                BaseDeDatos.tablaPais!!.actualizarPaisFormulario(
                    nombre.text.toString(),
                    idioma.text.toString(),
                    moneda.text.toString(),
                    precioDolar.text.toString().toDouble(),
                    id
                )
                finish()
            }

        if (idPais !=0){
            //ocultar boton crear
            botonCrearBDD
                .visibility = Button.INVISIBLE
        }else{
            //ocultar boton actualizar
            botonActualizarBDD
                .visibility = Button.INVISIBLE
        }
    }

    override fun onResume() {
        super.onResume()
    }

    //si idPais es distinto de 0, entonces se va a actualizar
    fun llenarDatosFormulario(idPais: Int){
        if(idPais != 0){
            val paisEncontrado = BaseDeDatos.tablaPais!!.consultarPaisPorID(idPais)
            val nombre = findViewById<EditText>(R.id.input_nombre_pais)
            val idioma = findViewById<EditText>(R.id.input_idioma)
            val moneda = findViewById<EditText>(R.id.input_moneda)
            val precioDolar = findViewById<EditText>(R.id.input_precioDolar)
            nombre.setText(paisEncontrado.nombre)
            idioma.setText(paisEncontrado.idioma)
            moneda.setText(paisEncontrado.moneda)
            precioDolar.setText(paisEncontrado.precioDolar.toString())
        }
    }
}