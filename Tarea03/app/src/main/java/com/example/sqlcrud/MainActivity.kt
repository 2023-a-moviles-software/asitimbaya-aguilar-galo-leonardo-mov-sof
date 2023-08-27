package com.example.sqlcrud

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        BaseDeDatos.tablaPais = SqliteHelper(this)
        BaseDeDatos.tablaLugarTuristico = SqliteHelper(this)

        val botonPais = findViewById<Button>(R.id.btn_Pais)
        botonPais
            .setOnClickListener {
                irActividad(
                    PaisesActivity::class.java
                )
            }
    }

    fun irActividad(
        clase: Class<*>
    ) {
        val intent = Intent(this, clase)
        // NO RECIBIMOS RESPUESTA
        startActivity(intent)
        // this.startActivity()
    }
}