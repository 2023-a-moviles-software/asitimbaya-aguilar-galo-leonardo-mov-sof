package com.example.exameniib

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CRUDPais : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    private var documentoId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crudpais)

        db = Firebase.firestore

        val nombrePais = intent.getStringExtra("nombre")

        val nombre = findViewById<EditText>(R.id.input_nombre_pais)
        val idioma = findViewById<EditText>(R.id.input_idioma)
        val moneda = findViewById<EditText>(R.id.input_moneda)
        val precioDolar = findViewById<EditText>(R.id.input_precioDolar)

        llenarDatosFormulario(nombrePais)

        val botonCrearBDD = findViewById<Button>(R.id.btn_crear_bdd)
        botonCrearBDD.setOnClickListener {
            val pais = hashMapOf(
                "nombre" to nombre.text.toString(),
                "idioma" to idioma.text.toString(),
                "moneda" to moneda.text.toString(),
                "precioDolar" to precioDolar.text.toString().toDouble()
            )

            db.collection("paises")
                .add(pais)
                .addOnSuccessListener {
                    finish()
                }
                .addOnFailureListener {
                    // Log the error
                }
        }

        val botonActualizarBDD = findViewById<Button>(R.id.btn_actualizar_bdd)
        botonActualizarBDD.setOnClickListener {
            documentoId?.let { id ->
                val paisActualizado = hashMapOf(
                    "nombre" to nombre.text.toString(),
                    "idioma" to idioma.text.toString(),
                    "moneda" to moneda.text.toString(),
                    "precioDolar" to precioDolar.text.toString().toDouble()
                )

                db.collection("paises")
                    .document(id)
                    .update(paisActualizado as Map<String, Any>)
                    .addOnSuccessListener {
                        finish()
                    }
                    .addOnFailureListener {
                        // Log the error
                    }
            }
        }

        if (nombrePais != null) {
            botonCrearBDD.visibility = Button.INVISIBLE
        } else {
            botonActualizarBDD.visibility = Button.INVISIBLE
        }
    }

    fun llenarDatosFormulario(nombrePais: String?) {
        if(nombrePais != null){
            db.collection("paises")
                .whereEqualTo("nombre", nombrePais)
                .get()
                .addOnSuccessListener { documents ->
                    if (documents.documents.isNotEmpty()) {
                        val pais = documents.documents[0]

                        val nombre = findViewById<EditText>(R.id.input_nombre_pais)
                        val idioma = findViewById<EditText>(R.id.input_idioma)
                        val moneda = findViewById<EditText>(R.id.input_moneda)
                        val precioDolar = findViewById<EditText>(R.id.input_precioDolar)

                        nombre.setText(pais.getString("nombre"))
                        idioma.setText(pais.getString("idioma"))
                        moneda.setText(pais.getString("moneda"))
                        precioDolar.setText(pais.getDouble("precioDolar").toString())

                        // Guardar el ID del documento para usarlo durante la actualización
                        documentoId = pais.id
                    }
                }
                .addOnFailureListener { exception ->
                    // Log the error
                }
        }
    }
}
