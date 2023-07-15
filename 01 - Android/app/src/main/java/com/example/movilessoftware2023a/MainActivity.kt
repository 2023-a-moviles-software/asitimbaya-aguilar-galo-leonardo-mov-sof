package com.example.movilessoftware2023a

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import org.jetbrains.annotations.Contract

class MainActivity : AppCompatActivity() {

    //Recibir respuesta del intent
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

    val callBackIntentPickUri = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode === RESULT_OK) {
            if (result.data != null) {
                val uri: Uri = result.data!!.data!!
                val cursor = contentResolver.query(
                    uri, null, null,
                    null, null, null
                )
                cursor?.moveToFirst()
                val indiceTelefono = cursor?.getColumnIndex(
                    ContactsContract.CommonDataKinds.Phone.NUMBER
                )
                val telefono = cursor?.getString(indiceTelefono!!)
                cursor?.close()
                "Telefono ${telefono}"
            }
        }
}

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    //Base de Datos sqlite
    //Main Activity
    EBaseDeDatos.tablaEntrenador = ESqliteHelperEntrenador(this)


    val botonCicloVida = findViewById<Button>(
        R.id.btn_ciclo_vida
    )
    botonCicloVida.setOnClickListener {
        irActividad(AACicloVida::class.java)
    }

    val botonListView = findViewById<Button>(
        R.id.btn_ir_list_view
    )

    botonListView.setOnClickListener {
        irActividad(BListView::class.java)
    }

    val botonIntentImplicito = findViewById<Button>(R.id.btn_ir_intent_implicito)
    botonIntentImplicito.setOnClickListener {
        val intentConRespuesta = Intent(
            Intent.ACTION_PICK,
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        )
        callBackIntentPickUri.launch(intentConRespuesta)
    }

    val botonIntentExplicito = findViewById<Button>(R.id.btn_ir_intent_explicito)
    botonIntentExplicito.setOnClickListener {
        abrirActividadConParametros(CIntentExplicitoParametros::class.java)
    }

    val botonSQlite = findViewById<Button>(R.id.btn_sqlite)
    botonSQlite.setOnClickListener {
        irActividad(ECrudEntrenador::class.java)
    }

    val botonRView= findViewById<Button>(R.id.btn_recycler_view)
    botonRView.setOnClickListener {
        irActividad(FRecyclerView::class.java)
    }
}


fun irActividad(
    clase: Class<*>
) {
    val intent = Intent(this, clase)
    //NO RECIBIMOS RESPUESTA
    startActivity(intent)
    // this.startActivity()
}
     
fun abrirActividadConParametros(
    clase: Class<*>
) {
    val intentExplicito = Intent(this, clase)
    //Enviar parametros
    //(aceptamos primitivas)
    intentExplicito.putExtra("nombre", "Leo")
    intentExplicito.putExtra("apellido", "Asitimbaya")
    intentExplicito.putExtra("edad", 22)
    //enviamos el intent con RESPUESTA
    //RECIBIMOS RESPUESTA
    callbackContenidoIntentExplicito.launch(intentExplicito)
}
}