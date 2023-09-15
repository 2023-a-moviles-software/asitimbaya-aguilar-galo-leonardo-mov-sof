package com.example.autoassist.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import com.example.autoassist.R
import com.example.autoassist.model.Mantenimiento
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore

class RegistroActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        val email = intent.getStringExtra("email") ?: ""
        val idMantenimiento = intent.getStringExtra("idMantenimiento") ?: ""
        val botonCrear = findViewById<Button>(R.id.btn_crear)
        val botonActualizar = findViewById<Button>(R.id.btn_actualizar)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_inicio -> {
                    // Navega a tu fragmento o actividad de "Inicio"
                }
                R.id.navigation_mantenimiento -> {
                    irActividad(RegistroActivity::class.java)
                }
                R.id.navigation_combustible -> {
                    // Navega a tu fragmento o actividad de "Combustible"
                }
                R.id.navigation_perfil -> {
                    // Navega a tu fragmento o actividad de "Perfil"
                }
            }
            true
        }


        llenarDatosFormulario(email, idMantenimiento)
        if (idMantenimiento.isNotEmpty()) {
            //ocultar boton crear
            botonCrear.visibility = Button.INVISIBLE
        } else {
            //ocultar boton actualizar
            botonActualizar.visibility = Button.INVISIBLE
        }


        botonCrear.setOnClickListener {
            try {
                val tipoMantenimiento = findViewById<EditText>(R.id.campo1).text.toString()
                val kmActual = findViewById<EditText>(R.id.campo2).text.toString()
                val fecha = findViewById<EditText>(R.id.campo3).text.toString()
                val nombreMecanica = findViewById<EditText>(R.id.campo4).text.toString()
                val detalle = findViewById<EditText>(R.id.campo5).text.toString()

                val nuevoMantenimiento = hashMapOf(
                    "tipoMantenimiento" to tipoMantenimiento,
                    "kmActual" to kmActual,
                    "fechaMantenimiento" to fecha,
                    "nombreMecanica" to nombreMecanica,
                    "detalle" to detalle
                )

                // Primero encontramos el ID del documento del usuario usando email
                db.collection("usuarios")
                    .whereEqualTo("email", email)
                    .get()
                    .addOnSuccessListener { documentos ->
                        if (documentos.documents.isNotEmpty()) {
                            val usuarioId = documentos.documents[0].id

                            // Luego, con el ID del usuario, creamos un nuevo mantenimiento en la subcolección correspondiente
                            db.collection("usuarios").document(usuarioId)
                                .collection("vehiculos").document("1")
                                .collection("mantenimientos")
                                .add(nuevoMantenimiento)
                                .addOnSuccessListener {
                                    finish()
                                }
                                .addOnFailureListener { e ->
                                    mostrarSnackBar("Error, datos incorrectos: ${e.message}")
                                }
                        } else {
                            mostrarSnackBar("Usuario no encontrado" + email)
                        }
                    }
                    .addOnFailureListener { e ->
                        mostrarSnackBar("Error, datos incorrectos: ${e.message}")
                    }
            } catch (e: Exception) {
                mostrarSnackBar("Error, datos incorrectos: ${e.message}")
            }
        }

        botonActualizar.setOnClickListener {
            try {
                val tipoMantenimiento = findViewById<EditText>(R.id.campo1).text.toString()
                val kmActual = findViewById<EditText>(R.id.campo2).text.toString()
                val fecha = findViewById<EditText>(R.id.campo3).text.toString()
                val nombreMecanica = findViewById<EditText>(R.id.campo4).text.toString()
                val detalle = findViewById<EditText>(R.id.campo5).text.toString()

                // Primero, encontramos el ID del usuario usando el email
                db.collection("usuarios")
                    .whereEqualTo("email", email)
                    .get()
                    .addOnSuccessListener { documentos ->
                        if (documentos.documents.isNotEmpty()) {
                            val usuarioId = documentos.documents[0].id

                            // Luego, con el ID del usuario, actualizamos el mantenimiento en la subcolección correspondiente
                            db.collection("usuarios").document(usuarioId)
                                .collection("vehiculos").document("1")
                                .collection("mantenimientos").document(idMantenimiento)
                                .update(
                                    hashMapOf(
                                        "tipoMantenimiento" to tipoMantenimiento,
                                        "kmActual" to kmActual,
                                        "fechaMantenimiento" to fecha,
                                        "nombreMecanica" to nombreMecanica,
                                        "detalle" to detalle
                                    ) as Map<String, Any>
                                )
                                .addOnSuccessListener {
                                    finish()
                                }
                                .addOnFailureListener { e ->
                                    mostrarSnackBar("Error, datos incorrectos: ${e.message}")
                                }
                        } else {
                            mostrarSnackBar("Usuario no encontrado" + email)
                        }
                    }
                    .addOnFailureListener { e ->
                        mostrarSnackBar("Error, datos incorrectos: ${e.message}")
                    }
            } catch (e: Exception) {
                mostrarSnackBar("Error, datos incorrectos: ${e.message}")
            }
        }
    }

    private fun llenarDatosFormulario(email: String, idMantenimiento: String) {
        // Verifica que los parámetros necesarios no estén vacíos
        if (email.isNotBlank() && idMantenimiento.isNotBlank()) {
            // Hace una consulta a la colección de usuarios filtrando por el email proporcionado
            db.collection("usuarios").whereEqualTo("email", email).get()
                .addOnSuccessListener { documentosUsuario ->
                    if (documentosUsuario.documents.isNotEmpty()) {
                        val usuarioId = documentosUsuario.documents[0].id

                        // Con el ID del usuario obtenido, ahora hace una consulta para obtener el vehículo con ID "1"
                        db.collection("usuarios").document(usuarioId)
                            .collection("vehiculos").document("1")
                            .collection("mantenimientos").document(idMantenimiento).get()
                            .addOnSuccessListener { documentoMantenimiento ->
                                if (documentoMantenimiento.exists()) {
                                    val mantenimiento = documentoMantenimiento.toObject(Mantenimiento::class.java)

                                    val tipoMantenimiento: EditText = findViewById(R.id.campo1)
                                    val kmActual: EditText = findViewById(R.id.campo2)
                                    val fecha: EditText = findViewById(R.id.campo3)
                                    val nombreMecanica: EditText = findViewById(R.id.campo4)
                                    val detalle: EditText = findViewById(R.id.campo5)


                                    tipoMantenimiento.setText(mantenimiento?.tipoMantenimiento)
                                    kmActual.setText(mantenimiento?.kmActual)
                                    fecha.setText(mantenimiento?.fechaMantenimiento)
                                    nombreMecanica.setText(mantenimiento?.nombreMecanica)
                                    detalle.setText(mantenimiento?.detalle)

                                } else {
                                    // El documento de mantenimiento no se encontró
                                    mostrarSnackBar("El documento de mantenimiento no se encontró")
                                }
                            }
                            .addOnFailureListener {
                            // Maneja el error de falla al obtener el documento de mantenimiento aquí
                                mostrarSnackBar("Error obteniendo documentos: $it")
                            }
                    } else {
                        // No se encontraron documentos para el email proporcionado
                        mostrarSnackBar("No se encontraron documentos para el email proporcionado")
                    }
                }
                .addOnFailureListener {
                    // Maneja el error de falla al obtener los documentos del usuario aquí
                    mostrarSnackBar("Error obteniendo documentos: $it")
                }
        } else {
            // Los parámetros necesarios están vacíos, mostrar un mensaje o manejar este caso de alguna manera
            mostrarSnackBar("Los parámetros necesarios están vacíos")
        }
    }



    fun mostrarSnackBar(texto: String) {
        Snackbar.make(
            findViewById(R.id.cl_registro),
            texto,
            Snackbar.LENGTH_LONG
        )
            .setAction("Action", null).show()
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
