package com.example.exameniib

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import com.example.exameniib.models.LugarTuristico
import com.example.exameniib.models.Pais
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LugaresTuristicosActivity : AppCompatActivity() {

    val query: Query? = null
    val arregloLugar = arrayListOf<LugarTuristico>()
    lateinit var listView: ListView
    lateinit var adaptador: ArrayAdapter<LugarTuristico>

    private val db = FirebaseFirestore.getInstance()

    private var lugarSeleccionado: LugarTuristico? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lugares_turisticos)

        val botonCrearLugarTuristico = findViewById<Button>(R.id.btn_crearLugar)
        botonCrearLugarTuristico.setOnClickListener {
           abrirActividadConParametros(
                CRUDLugarTuristico::class.java,
                intent.getStringExtra("nombre") ?: "",
               lugarSeleccionado?.nombreLT ?: ""
           )
        }
        // Configurando el list view
        listView = findViewById<ListView>(R.id.lv_lugares)
        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arregloLugar
        )
        listView.adapter = adaptador
        llenarDatos()

        registerForContextMenu(listView)
    }

    private fun llenarDatos() {
        val nombrePais = intent.getStringExtra("nombre") ?: ""
        val nombrePais2 = findViewById<TextView>(R.id.tv_paisSelected)
        nombrePais2.text = nombrePais

        db.collection("paises")
            .whereEqualTo("nombre", nombrePais)
            .get()
            .addOnSuccessListener { documentos ->
                if (documentos.documents.isNotEmpty()) {
                    val idPais = documentos.documents[0].id
                    obtenerLugares(idPais)
                } else {
                    Log.e("Firebase", "No se encontró el país con nombre: $nombrePais")
                }
            }
            .addOnFailureListener { exception ->
                Log.e("Firebase", "Error obteniendo documentos: ", exception)
            }
    }

    private fun obtenerLugares(idPais: String) {
        db.collection("paises")
            .document(idPais)
            .collection("lugares")
            .get()
            .addOnSuccessListener { documentos ->
                arregloLugar.clear()
                for (documento in documentos) {
                    documento.id
                    añadirALugarTuristico(documento)
                }
                adaptador.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Log.e("Firebase", "Error obteniendo documentos: ", exception)
            }
    }

    fun añadirALugarTuristico(lugar: QueryDocumentSnapshot) {
        val nuevoLugarTuristico = LugarTuristico(
            lugar.data["nombreLT"] as String?,
            lugar.data["costoEntrada"] as Number?,
            lugar.data["disponible"] as Boolean?,
        )
        Log.i("NuevoLugarTuristico", "Nuevo lugar turístico: $nuevoLugarTuristico")
        arregloLugar.add(nuevoLugarTuristico)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val nombre = intent.getStringExtra("nombre") ?: ""
        val nombreLugar = lugarSeleccionado?.nombreLT ?: return super.onContextItemSelected(item)

        return when (item.itemId) {
            R.id.mi_editar -> {
                abrirActividadConParametros(
                    CRUDLugarTuristico::class.java,
                    nombre,
                    nombreLugar)
                true
            }
            R.id.mi_eliminar -> {
                eliminarLugar(nombre, nombreLugar)
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }






    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menulugar, menu)

        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        lugarSeleccionado = listView.adapter.getItem(info.position) as LugarTuristico
    }



    fun eliminarLugar(nombrePais: String, nombreLT: String) {
        // Primero, obtenemos el ID del país usando el nombrePais
        db.collection("paises")
            .whereEqualTo("nombre", nombrePais)
            .get()
            .addOnSuccessListener { documentosPais ->
                if (documentosPais.documents.isNotEmpty()) {
                    val idPais = documentosPais.documents[0].id

                    // Luego, obtenemos el ID del lugar usando el nombreLT
                    db.collection("paises")
                        .document(idPais)
                        .collection("lugares")
                        .whereEqualTo("nombreLT", nombreLT)
                        .get()
                        .addOnSuccessListener { documentosLugar ->
                            if (documentosLugar.documents.isNotEmpty()) {
                                val idLugar = documentosLugar.documents[0].id

                                // Ahora que tenemos ambos ID, podemos eliminar el lugar
                                db.collection("paises")
                                    .document(idPais)
                                    .collection("lugares")
                                    .document(idLugar)
                                    .delete()
                                    .addOnSuccessListener {
                                        Log.i("Firebase", "Lugar turístico eliminado con éxito!")
                                        llenarDatos() // Recargar la lista luego de eliminar un lugar turístico
                                    }
                                    .addOnFailureListener { exception ->
                                        Log.e("Firebase", "Error eliminando el lugar turístico: ", exception)
                                    }
                            } else {
                                Log.e("Firebase", "No se encontró el lugar turístico con nombreLT: $nombreLT")
                            }
                        }
                        .addOnFailureListener { exception ->
                            Log.e("Firebase", "Error obteniendo documentos: ", exception)
                        }
                } else {
                    Log.e("Firebase", "No se encontró el país con nombre: $nombrePais")
                }
            }
            .addOnFailureListener { exception ->
                Log.e("Firebase", "Error obteniendo documentos: ", exception)
            }
    }




    fun abrirActividadConParametros(clase: Class<*>, nombrePais: String, nombreLugar: String) {
        val intentExplicito = Intent(this, clase)
        // Enviar parámetros
        intentExplicito.putExtra("nombreP", nombrePais)
        intentExplicito.putExtra("nombreL", nombreLugar)
        // Iniciar la actividad
        startActivity(intentExplicito)
    }

    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(this, clase)
        // NO RECIBIMOS RESPUESTA
        startActivity(intent)
        // this.startActivity()
    }

    override fun onResume() {
        super.onResume()
        llenarDatos()
    }
}