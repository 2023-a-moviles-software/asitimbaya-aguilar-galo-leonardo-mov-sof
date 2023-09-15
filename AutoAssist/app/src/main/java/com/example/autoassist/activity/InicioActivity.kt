package com.example.autoassist.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.autoassist.R
import com.example.autoassist.model.Mantenimiento
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot

class InicioActivity : AppCompatActivity() {

    val arregloMantenimiento = arrayListOf<Mantenimiento>()
    lateinit var listView: ListView
    lateinit var adaptador: ArrayAdapter<Mantenimiento>

    private val db = FirebaseFirestore.getInstance()

    private var mantenimientoSeleccionado: Mantenimiento? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_inicio -> {
                    // Navega a tu fragmento o actividad de "Inicio"
                }
                R.id.navigation_mantenimiento -> {
                    abrirActividadConParametros(
                        RegistroActivity::class.java,
                        intent.getStringExtra("email") ?: "",
                        ""
                    )
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


        // Configurando el list view
        listView = findViewById<ListView>(R.id.lv_mantenimientos)
        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arregloMantenimiento
        )
        listView.adapter = adaptador
        llenarDatos()
        registerForContextMenu(listView)
    }

    private fun llenarDatos() {
        val email = intent.getStringExtra("email") ?: ""


        db.collection("usuarios")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { documentos ->
                if (documentos.documents.isNotEmpty()) {
                    val idUser = documentos.documents[0].id
                    obtenerMantenimientos(idUser)
                } else {
                    Log.e("Firebase", "No se encontró el usuario con email: $email")
                    mostrarSnackBar("No se encontró el usuario con email: $email")
                }
            }
            .addOnFailureListener { exception ->
                Log.e("Firebase", "Error obteniendo documentos: ", exception)
                mostrarSnackBar("Error obteniendo documentos: $exception")
            }
    }

    private fun obtenerMantenimientos(idUser: String) {
        db.collection("usuarios")
            .document(idUser)
            .collection("vehiculos")
            .document("1")
            .collection("mantenimientos")
            .get()
            .addOnSuccessListener { documentos ->
                arregloMantenimiento.clear()
                for (documento in documentos) {
                    documento.id
                    añadirAMantenimientos(documento)
                }
                adaptador.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Log.e("Firebase", "Error obteniendo documentos: ", exception)
            }
    }

    fun añadirAMantenimientos(mantenimiento: QueryDocumentSnapshot) {
        val nuevoMantenimiento = Mantenimiento(
            mantenimiento.id,
            mantenimiento.data["tipoMantenimiento"] as String?,
            mantenimiento.data["kmActual"] as String?,
            mantenimiento.data["fechaMantenimiento"] as String?,
            mantenimiento.data["nombreMecanica"] as String?,
            mantenimiento.data["detalle"] as String?

        )

        arregloMantenimiento.add(nuevoMantenimiento)
    }


    override fun onContextItemSelected(item: MenuItem): Boolean {
        val email = intent.getStringExtra("email") ?: ""
        val idMantenimiento =
            mantenimientoSeleccionado?.id ?: return super.onContextItemSelected(item)

        return when (item.itemId) {
            R.id.mi_editar -> {
                abrirActividadConParametros(
                    RegistroActivity::class.java,
                    email,
                    idMantenimiento
                )
                true
            }

            R.id.mi_eliminar -> {
                eliminarMantenimiento(email, idMantenimiento)
                true
            }

            else -> super.onContextItemSelected(item)
        }
    }

    private fun eliminarMantenimiento(email: String, idMantenimiento: String) {
        db.collection("usuarios")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { documentos ->
                if (documentos.documents.isNotEmpty()) {
                    val idUser = documentos.documents[0].id
                    db.collection("usuarios")
                        .document(idUser)
                        .collection("vehiculos")
                        .document("1")
                        .collection("mantenimientos")
                        .document(idMantenimiento)
                        .delete()
                        .addOnSuccessListener {
                            Log.i("firebase-firestore", "DocumentSnapshot successfully deleted!")
                            mostrarSnackBar("Mantenimiento eliminado")
                            llenarDatos()
                        }
                        .addOnFailureListener {
                            Log.i("firebase-firestore", "Error deleting document")
                            mostrarSnackBar("Error eliminando mantenimiento")
                        }
                } else {
                    Log.i("firebase-firestore", "No document found with the name: $email")
                    mostrarSnackBar("No se encontró el usuario con email: $email")
                }
            }
            .addOnFailureListener { exception ->
                Log.i("firebase-firestore", "Error getting documents: ", exception)
                mostrarSnackBar("Error obteniendo documentos: $exception")
            }

    }


    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_mantenimiento, menu)

        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        mantenimientoSeleccionado = listView.adapter.getItem(info.position) as Mantenimiento
    }

    fun abrirActividadConParametros(clase: Class<*>, email: String, id: String) {
        val intentExplicito = Intent(this, clase)
        // Enviar parámetros
        intentExplicito.putExtra("email", email)
        intentExplicito.putExtra("idMantenimiento", id)
        // Iniciar la actividad
        startActivity(intentExplicito)
    }

    fun mostrarSnackBar(texto: String) {
        Snackbar.make(
            findViewById(R.id.cl_inicio),
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
    override fun onResume() {
        super.onResume()
        llenarDatos()
    }

    private fun loadImage() {
//        val imagen =
//            "https://images.patiotuerca.com/thumbs/w/260x153xC/amz_ptf_ecuador/1182023/1765924/o_o/pt_1765924_12152858.jpg"
//        Glide.with(this).load(imagen).into(imagenVehiculo)
    }
}