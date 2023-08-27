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

class PaisesActivity : AppCompatActivity() {

    lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paises)
        val botonCrearPais = findViewById<Button>(R.id.btn_crear_pais)
        botonCrearPais.setOnClickListener {
            irActividad(CRUDPais::class.java)
        }
        actualizarLista()
    }




    override fun onContextItemSelected(item: MenuItem): Boolean {
        val idReal = paisSeleccionado?.id ?: return super.onContextItemSelected(item)

        return when(item.itemId){
            R.id.mi_editar ->{
                abrirActividadConParametros(
                    CRUDPais::class.java,
                    idReal
                )
                return true
            }
            R.id.mi_eliminar ->{
                //eliminar idItemSeleccionado del arreglo
                BaseDeDatos.tablaPais!!.eliminarPaisFormulario(
                    idReal
                )
                actualizarLista()
                return true
            }
            R.id.mi_verLugares ->{
                abrirActividadConParametros(
                    LugaresTuristicosActivity::class.java,
                    idReal
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


    fun abrirActividadConParametros(clase: Class<*>, id: Int) {
        val intentExplicito = Intent(this, clase)
        // Enviar parámetros
        intentExplicito.putExtra("id", id)
        // Iniciar la actividad
        startActivity(intentExplicito)
    }


    fun actualizarLista(){
        val arregloPais = BaseDeDatos.tablaPais!!.consultarPaises()
        //Adaptador
        listView = findViewById<ListView>(R.id.lv_paises)

        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arregloPais
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        registerForContextMenu(listView)
    }
    override fun onResume() {
        super.onResume()
        actualizarLista()
    }
}