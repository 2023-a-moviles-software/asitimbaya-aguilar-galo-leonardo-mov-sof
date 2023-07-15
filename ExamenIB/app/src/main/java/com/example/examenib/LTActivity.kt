package com.example.examenib

import android.annotation.SuppressLint
import android.app.Activity
import android.content.DialogInterface
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
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar

class LTActivity : AppCompatActivity() {

    val arregloLugarTuristico = BaseDatosMemoria.arregloLugarTuristico
    var idItemSeleccionado = 0
    val arregloLugar = arrayListOf<LugarTuristico>()
    var idLugarSeleccionado = 0
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
        setContentView(R.layout.activity_ltactivity)

        val botonCrearLugarTuristico = findViewById<Button>(R.id.btn_crearLugar)
        botonCrearLugarTuristico.setOnClickListener {
            abrirActividadConParametros(
                CULugarTuristico::class.java,
                0
            )
        }
        llenarDatos()
    }




    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.mi_editar ->{
                abrirActividadConParametros(

                    CULugarTuristico::class.java,
                    idLugarSeleccionado+1
                )
                return true
            }
            R.id.mi_eliminar ->{
                //eliminar idItemSeleccionado del arreglo
                eliminarLugar()


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
        inflater.inflate(R.menu.menult, menu)
        //Obtener el id del array list seleccionado
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        idLugarSeleccionado = arregloLugar[idItemSeleccionado].idLT!!-1
    }

    fun abrirActividadConParametros(
        clase: Class<*>,
        id: Int
    ) {
        val intentExplicito = Intent(this, clase)
        //Enviar parametros
        //(aceptamos primitivas)
        intentExplicito.putExtra("idLugar", id)
        intentExplicito.putExtra("id", intent.getIntExtra("id", 0))

        //enviamos el intent con RESPUESTA
        //RECIBIMOS RESPUESTA
        callbackContenidoIntentExplicito.launch(intentExplicito)
    }

    fun eliminarLugar(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿Desea eliminar?")
        builder.setPositiveButton(
            "Aceptar",
            DialogInterface.OnClickListener { //Callback
                    dialog, which ->
                //Eliminar lugar seleccionada
                try {
                    idLugarSeleccionado?.let { arregloLugarTuristico.removeAt(it) }
                    arregloLugar.removeAt(idItemSeleccionado)
                    llenarDatos()
                    mostrarSnackBar("Eliminado con exito"+idLugarSeleccionado+"/"+idItemSeleccionado)
                }catch (e: Exception){
                    mostrarSnackBar("Error al eliminar"+idLugarSeleccionado+"/"+idItemSeleccionado)
                }
            }
        )
        builder.setNegativeButton("Cancelar", null)
        val dialogo = builder.create()
        dialogo.show()
    }

    fun llenarDatos(){
        val idPaisSelected = intent.getIntExtra("id", 0)
        arregloLugar.clear()
        for (lugar in arregloLugarTuristico){
            if (lugar.idPais == idPaisSelected){
                arregloLugar.add(lugar)
            }
        }
        val nombrePais: String? = BaseDatosMemoria.arregloPais[idPaisSelected-1].nombre

        findViewById<TextView>(R.id.tv_paisSelected).text = nombrePais
        val listView = findViewById<ListView>(R.id.lv_lugares)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arregloLugar
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