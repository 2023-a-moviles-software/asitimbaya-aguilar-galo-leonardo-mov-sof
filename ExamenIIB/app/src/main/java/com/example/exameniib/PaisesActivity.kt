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
import com.example.exameniib.models.Pais
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class PaisesActivity : AppCompatActivity() {
    val query: Query? = null
    val arreglo = arrayListOf<Pais>()
    lateinit var listView: ListView
    lateinit var adaptador: ArrayAdapter<Pais>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paises)

        val botonCrearPais = findViewById<Button>(R.id.btn_crear_pais)
        botonCrearPais.setOnClickListener {
            irActividad(CRUDPais::class.java)
        }
        // Configurando el list view
        listView = findViewById<ListView>(R.id.lv_paises)
        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arreglo
        )
        listView.adapter = adaptador
        consultarPaises(adaptador)
        adaptador.notifyDataSetChanged()
        registerForContextMenu(listView)
    }

    fun consultarPaises(adaptador: ArrayAdapter<Pais>) {
        val db = Firebase.firestore
        val paisesRefUnico = db.collection("paises")
        paisesRefUnico
            .get()
            .addOnSuccessListener { // it => eso (lo que llegue)
                limpiarArreglo() // Limpia el arreglo aquí
                for (pais in it){
                    pais.id
                    anadirAArregloPais(pais)
                }
                adaptador.notifyDataSetChanged() // Notifica los cambios aquí
            }
            .addOnFailureListener {
                // Errores
            }
    }


    fun eliminarPais(nombre: String) {
        val db = Firebase.firestore
        val paisesRefUnico = db.collection("paises")

        paisesRefUnico
            .whereEqualTo("nombre", nombre)
            .get()
            .addOnSuccessListener { result ->
                if (result.documents.isNotEmpty()) {
                    // Obtener el ID del primer documento en los resultados y eliminarlo
                    val documentId = result.documents[0].id
                    paisesRefUnico
                        .document(documentId)
                        .delete()
                        .addOnSuccessListener {
                            Log.i("firebase-firestore", "DocumentSnapshot successfully deleted!")
                        }
                        .addOnFailureListener {
                            Log.i("firebase-firestore", "Error deleting document")
                        }
                } else {
                    Log.i("firebase-firestore", "No document found with the name: $nombre")
                }
            }
            .addOnFailureListener { exception ->
                Log.i("firebase-firestore", "Error getting documents: ", exception)
            }
    }




    fun limpiarArreglo() {arreglo.clear()}
    fun anadirAArregloPais(
        pais: QueryDocumentSnapshot
    ){
        // ciudad.id
        val nuevoPais = Pais(
            pais.data.get("nombre") as String?,
            pais.data.get("idioma") as String?,
            pais.data.get("moneda") as String?,
            pais.data.get("precioDolar") as Number?
        )
        arreglo.add(nuevoPais)
    }


    override fun onContextItemSelected(item: MenuItem): Boolean {
        val nombreReal = paisSeleccionado?.nombre ?: return super.onContextItemSelected(item)

        return when(item.itemId){
            R.id.mi_editar ->{
                abrirActividadConParametros(
                    CRUDPais::class.java,
                    nombreReal
                )
                return true
            }
            R.id.mi_eliminar ->{
                //eliminar idItemSeleccionado del arreglo
                eliminarPais(nombreReal)
                consultarPaises(adaptador)
                return true
            }
            R.id.mi_verLugares ->{
                abrirActividadConParametros(
                    LugaresTuristicosActivity::class.java,
                    nombreReal
                )
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }


    var paisSeleccionado: Pais? = null

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menupais, menu)

        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        paisSeleccionado = listView.adapter.getItem(info.position) as Pais
    }



    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(this, clase)
        // NO RECIBIMOS RESPUESTA
        startActivity(intent)
        // this.startActivity()
    }


    fun abrirActividadConParametros(clase: Class<*>, nombre: String) {
        val intentExplicito = Intent(this, clase)
        // Enviar parámetros
        intentExplicito.putExtra("nombre", nombre)
        // Iniciar la actividad
        startActivity(intentExplicito)
    }


    override fun onResume() {
        super.onResume()
        consultarPaises(adaptador)
    }
}