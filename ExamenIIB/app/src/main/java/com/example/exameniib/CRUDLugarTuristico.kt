package com.example.exameniib

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore

class CRUDLugarTuristico : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private var documentoId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crudlugar_turistico)

        val nombrePais = intent.getStringExtra("nombreP") ?: ""
        val nombreLugar = intent.getStringExtra("nombreL") ?: ""
        llenarDatosFormulario(nombrePais, nombreLugar)
        val botonCrear = findViewById<Button>(R.id.btn_createLugar)
        botonCrear.setOnClickListener {
            try {
                val nombreLugarInput = findViewById<EditText>(R.id.input_nombreLugar).text.toString()
                val costoEntrada = findViewById<EditText>(R.id.input_costoLugar).text.toString().toDouble()
                val disponible: Boolean = findViewById<Switch>(R.id.sw_disponible).isChecked

                val nuevoLugar = hashMapOf(
                    "nombreLT" to nombreLugarInput,
                    "costoEntrada" to costoEntrada,
                    "disponible" to disponible
                )

                // Primero encontramos el ID del documento del país usando nombrePais
                db.collection("paises")
                    .whereEqualTo("nombre", nombrePais)
                    .get()
                    .addOnSuccessListener { documentos ->
                        if (documentos.documents.isNotEmpty()) {
                            val paisId = documentos.documents[0].id

                            // Luego, con el ID del país, creamos un nuevo lugar en la subcolección correspondiente
                            db.collection("paises").document(paisId)
                                .collection("lugares")
                                .add(nuevoLugar)
                                .addOnSuccessListener {
                                    finish()
                                }
                                .addOnFailureListener { e ->
                                    mostrarSnackBar("Error, datos incorrectos: ${e.message}")
                                }
                        } else {
                            mostrarSnackBar("País no encontrado" + nombrePais)
                        }
                    }
                    .addOnFailureListener { e ->
                        mostrarSnackBar("Error, datos incorrectos: ${e.message}")
                    }
            } catch (e: Exception) {
                mostrarSnackBar("Error, datos incorrectos: ${e.message}")
            }
        }


        val botonActualizarBDD = findViewById<Button>(R.id.btn_updateLugar)
        botonActualizarBDD.setOnClickListener {
            try {
                val nombreLugarInput = findViewById<EditText>(R.id.input_nombreLugar).text.toString()
                val costoEntrada = findViewById<EditText>(R.id.input_costoLugar).text.toString().toDouble()
                val disponible: Boolean = findViewById<Switch>(R.id.sw_disponible).isChecked

                // Primero, encontramos el ID del país usando el nombrePais
                db.collection("paises")
                    .whereEqualTo("nombre", nombrePais)
                    .get()
                    .addOnSuccessListener { documentos ->
                        if (documentos.documents.isNotEmpty()) {
                            val paisId = documentos.documents[0].id

                            // Luego, usamos el ID del país para encontrar y actualizar el lugar turístico
                            db.collection("paises").document(paisId)
                                .collection("lugares")
                                .whereEqualTo("nombreLT", nombreLugar)
                                .get()
                                .addOnSuccessListener { documentosLugar ->
                                    if (documentosLugar.documents.isNotEmpty()) {
                                        val lugarId = documentosLugar.documents[0].id

                                        db.collection("paises").document(paisId)
                                            .collection("lugares").document(lugarId)
                                            .update(
                                                "nombreLT", nombreLugarInput,
                                                "costoEntrada", costoEntrada,
                                                "disponible", disponible
                                            )
                                            .addOnSuccessListener {
                                                finish()
                                            }
                                    } else {
                                        mostrarSnackBar("Lugar no encontrado")
                                    }
                                }
                                .addOnFailureListener { e ->
                                    mostrarSnackBar("Error, datos incorrectos: ${e.message}")
                                }
                        } else {
                            mostrarSnackBar("País no encontrado")
                        }
                    }
                    .addOnFailureListener { e ->
                        mostrarSnackBar("Error, datos incorrectos: ${e.message}")
                    }
            } catch (e: Exception) {
                mostrarSnackBar("Error, datos incorrectos: ${e.message}")
            }
        }


        if (nombreLugar.isNotEmpty()) {
            //ocultar boton crear
            botonCrear.visibility = Button.INVISIBLE
        } else {
            //ocultar boton actualizar
            botonActualizarBDD.visibility = Button.INVISIBLE
        }
    }

    fun llenarDatosFormulario(nombrePais: String, nombreLugar: String) {
        if (nombrePais.isNotEmpty() && nombreLugar.isNotEmpty()) {
            db.collection("paises").whereEqualTo("nombre", nombrePais)
                .get()
                .addOnSuccessListener { documentosPais ->
                    if (documentosPais.documents.isNotEmpty()) {
                        val pais = documentosPais.documents[0]

                        db.collection("paises").document(pais.id)
                            .collection("lugares").whereEqualTo("nombreLT", nombreLugar)
                            .get()
                            .addOnSuccessListener { documentosLugar ->
                                if (documentosLugar.documents.isNotEmpty()) {
                                    val lugar = documentosLugar.documents[0]

                                    val nombreLugarTuristico = findViewById<EditText>(R.id.input_nombreLugar)
                                    val costoEntrada = findViewById<EditText>(R.id.input_costoLugar)
                                    val disponible = findViewById<Switch>(R.id.sw_disponible)

                                    nombreLugarTuristico.setText(lugar.getString("nombreLT"))
                                    costoEntrada.setText(lugar.getDouble("costoEntrada").toString())
                                    disponible.isChecked = lugar.getBoolean("disponible") ?: false
                                }
                            }
                    }
                }
                .addOnFailureListener { exception ->
                    // Log the error
                }
        }
    }



    fun mostrarSnackBar(texto: String) {
        Snackbar.make(
            findViewById(R.id.cl_CrudLT),
            texto,
            Snackbar.LENGTH_LONG
        )
            .setAction("Action", null).show()
    }
}
