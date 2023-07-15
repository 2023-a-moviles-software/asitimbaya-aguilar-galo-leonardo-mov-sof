package com.example.examenib

import android.app.Activity
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
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {
    val arregloPais = BaseDatosMemoria.arregloPais
    var idItemSeleccionado = 0
    val callbackContenidoIntentExplicito =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                if (result.data != null) {
                    //Logica Negocio
                    val data = result.data
                    "${data?.getStringExtra("nombreModificado")}"
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val botonCrearPais = findViewById<Button>(R.id.btn_crear_pais)
        botonCrearPais.setOnClickListener {
            irActividad(CUPais::class.java)
        }

        //Adaptador
        val listView = findViewById<ListView>(R.id.lv_paises)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arregloPais
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        registerForContextMenu(listView)
    }




    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.mi_editar ->{
                abrirActividadConParametros(
                    CUPais::class.java,
                    idItemSeleccionado+1
                )
                return true
            }
            R.id.mi_eliminar ->{
                //eliminar idItemSeleccionado del arreglo
                eliminarPais(idItemSeleccionado)

                return true
            }
            R.id.mi_verLugares ->{
                abrirActividadConParametros(
                    LTActivity::class.java,
                    idItemSeleccionado+1
                )
                return true
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
        //Llenar las opciones de menu
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        //Obtener el id del array list seleccionado
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        idItemSeleccionado = id
    }


    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(this, clase)
        // NO RECIBIMOS RESPUESTA
        startActivity(intent)
        // this.startActivity()
    }

    fun abrirActividadConParametros(
        clase: Class<*>,
        id: Int
    ) {
        val intentExplicito = Intent(this, clase)
        //Enviar parametros
        //(aceptamos primitivas)
        intentExplicito.putExtra("id", id)

        //enviamos el intent con RESPUESTA
        //RECIBIMOS RESPUESTA
        callbackContenidoIntentExplicito.launch(intentExplicito)
    }

    fun eliminarPais( id: Int){
        arregloPais.removeAt(id)
        //Adaptador
        val listView = findViewById<ListView>(R.id.lv_paises)
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
        val listView = findViewById<ListView>(R.id.lv_paises)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arregloPais
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        registerForContextMenu(listView)
    }
}