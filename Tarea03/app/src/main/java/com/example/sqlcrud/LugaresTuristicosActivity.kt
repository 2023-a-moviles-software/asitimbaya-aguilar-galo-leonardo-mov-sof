package com.example.sqlcrud

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

class LugaresTuristicosActivity : AppCompatActivity() {

    var idItemSeleccionado = 0
    val arregloLugar = arrayListOf<LugarTuristico>()
    var idLugarSeleccionado = 0
    lateinit var listView: ListView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lugares_turisticos)

        val botonCrearLugarTuristico = findViewById<Button>(R.id.btn_crearLugar)
        botonCrearLugarTuristico.setOnClickListener {
            abrirActividadConParametros(
                CRUDLugarTuristico::class.java,
                0
            )
        }
        llenarDatos()
    }




    override fun onContextItemSelected(item: MenuItem): Boolean {
        val idReal = lugarSeleccionado?.idLT ?: return super.onContextItemSelected(item)

        return when(item.itemId){
            R.id.mi_editar ->{
                abrirActividadConParametros(

                    CRUDLugarTuristico::class.java,
                    idReal
                )
                return true
            }
            R.id.mi_eliminar ->{
                BaseDeDatos.tablaLugarTuristico!!.eliminarLugarFormulario(
                    idReal
                )
                llenarDatos()
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }
    var lugarSeleccionado: LugarTuristico? = null
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        //Llenar las opciones de menu
        val inflater = menuInflater
        inflater.inflate(R.menu.menulugartur, menu)
        //Obtener el id del array list seleccionado
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        lugarSeleccionado = listView.adapter.getItem(info.position) as LugarTuristico

    }

    fun abrirActividadConParametros(clase: Class<*>, id: Int) {
        val intentExplicito = Intent(this, clase)
        // Enviar parámetros
        intentExplicito.putExtra("idLugar", id)
        intentExplicito.putExtra("id", intent.getIntExtra("id", 0))
        // Iniciar la actividad
        startActivity(intentExplicito)
    }

    fun llenarDatos(){
        val idPais = intent.getIntExtra("id", 0)
        val arregloLugarTuristico = BaseDeDatos.tablaLugarTuristico!!.consultarLugaresPorPais(idPais)
        val paisEncontrado = BaseDeDatos.tablaPais!!.consultarPaisPorID(idPais)
        val nombrePais = findViewById<TextView>(R.id.tv_paisSelected)
        nombrePais.text = paisEncontrado.nombre
        listView = findViewById<ListView>(R.id.lv_lugares)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arregloLugarTuristico
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        registerForContextMenu(listView)
    }

    override fun onResume() {
        super.onResume()
        llenarDatos()
    }
    fun mostrarSnackBar(texto: String) {
        Snackbar.make(
            findViewById(R.id.cl_LT),
            texto,
            Snackbar.LENGTH_LONG
        )
            .setAction("Action", null).show()
    }
}