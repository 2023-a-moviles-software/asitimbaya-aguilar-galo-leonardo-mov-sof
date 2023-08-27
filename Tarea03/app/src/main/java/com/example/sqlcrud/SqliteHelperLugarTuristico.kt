package com.example.sqlcrud

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.text.SimpleDateFormat

class SqliteHelperLugarTuristico(
    contexto: Context?, // this
) : SQLiteOpenHelper(
    contexto,
    "moviles", // nombre bdd
    null,
    4
) {
    val formatoFecha = SimpleDateFormat("dd/MM/yyyy")

    override fun onCreate(db: SQLiteDatabase?) {

        val scriptSQLCrearTablaLugarTuristico =
            """
                CREATE TABLE LUGARTURISTICO(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nombre VARCHAR(50),
                    costoEntrada DOUBLE,
                    fechaCreacion DATE,
                    disponible BOOLEAN,
                    idPais INTEGER,
                    FOREIGN KEY(idPais) REFERENCES PAIS(id)
                )
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaLugarTuristico)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS LUGARTURISTICO")
        onCreate(db)
    }



    fun crearLugar(
        nombre: String,
        costoEntrada: Double,
        fechaCreacion: String,
        disponible: Boolean,
        idPais: Int,

        ): Boolean {
        val baseDatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("costoEntrada", costoEntrada)
        valoresAGuardar.put("fechaCreacion", fechaCreacion)
        valoresAGuardar.put("disponible", disponible)
        valoresAGuardar.put("idPais", idPais)
        val resultadoGuardar = baseDatosEscritura
            .insert(
                "LUGARTURISTICO", // nombre tabla
                null,
                valoresAGuardar, // valores
            )
        baseDatosEscritura.close()
        return if (resultadoGuardar.toInt() === -1) false else true
    }

    fun eliminarLugarFormulario(id: Int): Boolean {
        val conexionEscritura = writableDatabase
        // where ID = ?
        val parametrosConsultaDelete = arrayOf(id.toString())
        val resultadoEliminacion = conexionEscritura
            .delete(
                "LUGARTURISTICO", // Nombre tabla
                "id=?", // Consulta Where
                parametrosConsultaDelete
            )
        conexionEscritura.close()
        return if (resultadoEliminacion.toInt() == -1) false else true
    }


    fun actualizarLugarFormulario(
        nombre: String,
        costoEntrada: Double,
        fechaCreacion: String,
        disponible: Boolean,
        idPais: Int,
        id: Int,
    ): Boolean {
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre", nombre)
        valoresAActualizar.put("costoEntrada", costoEntrada)
        valoresAActualizar.put("fechaCreacion", fechaCreacion)
        valoresAActualizar.put("disponible", disponible)
        valoresAActualizar.put("idPais", idPais)
        // where ID = ?
        val parametrosConsultaActualizar = arrayOf(id.toString())
        val resultadoActualizacion = conexionEscritura
            .update(
                "LUGARTURISTICO", // Nombre tabla
                valoresAActualizar, // Valores
                "id=?", // Consulta Where
                parametrosConsultaActualizar
            )
        conexionEscritura.close()
        return if (resultadoActualizacion.toInt() == -1) false else true
    }


    fun consultarLugarPorID(id: Int): LugarTuristico {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
            SELECT * FROM LUGARTURISTICO WHERE ID = ?
        """.trimIndent()
        val parametrosConsultaLectura = arrayOf(id.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura, // Consulta
            parametrosConsultaLectura, // Parametros
        )
        // logica busqueda
        val existeUsuario = resultadoConsultaLectura.moveToFirst()
        val usuarioEncontrado = LugarTuristico(0, "", 0.0, null, false, 0)
        val arreglo = arrayListOf<LugarTuristico>()
        if (existeUsuario) {
            do {
                val id = resultadoConsultaLectura.getInt(0) // Indice 0
                val nombre = resultadoConsultaLectura.getString(1)
                val costoEntrada = resultadoConsultaLectura.getDouble(2)
                val fechaCreacion = resultadoConsultaLectura.getString(3)
                val disponible = resultadoConsultaLectura.getInt(4)
                val idPais = resultadoConsultaLectura.getInt(5)
                if (id != null) {
                    // llenar el arreglo con un nuevo BEntrenador
                    usuarioEncontrado.idLT = id
                    usuarioEncontrado.nombreLT = nombre
                    usuarioEncontrado.costoEntrada = costoEntrada
                    //Cambiar a date
                    usuarioEncontrado.fechaCreacion = formatoFecha.parse(fechaCreacion)
                    //Cambiar a boolean
                    if (disponible == 1) {
                        usuarioEncontrado.disponible = true
                    }
                }
                usuarioEncontrado.idPais = idPais
            } while (resultadoConsultaLectura.moveToNext())
        }

        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return usuarioEncontrado

    }

    //listar lugares
    fun consultarLugaresTuristicos(): ArrayList<LugarTuristico> {
        val scriptConsultarLugares = "SELECT * FROM LUGARTURISTICO"
        val baseDatosLectura = readableDatabase
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultarLugares,
            null
        )
        val existeUsuario = resultadoConsultaLectura.moveToFirst()
        val arregloLugares = arrayListOf<LugarTuristico>()
        if (existeUsuario) {
            do {
                val id = resultadoConsultaLectura.getInt(0) // Indice 0
                val nombre = resultadoConsultaLectura.getString(1)
                val costoEntrada = resultadoConsultaLectura.getDouble(2)
                val fechaCreacion = resultadoConsultaLectura.getString(3)
                val disponible = resultadoConsultaLectura.getInt(4)
                val idPais = resultadoConsultaLectura.getInt(5)
                if (id != null) {
                    // llenar el arreglo con un nuevo BEntrenador
                    val lugarEncontrado = LugarTuristico(0, "", 0.0, null, false, 0)
                    lugarEncontrado.idLT = id
                    lugarEncontrado.nombreLT = nombre
                    lugarEncontrado.costoEntrada = costoEntrada
                    //Cambiar a date
                    lugarEncontrado.fechaCreacion = formatoFecha.parse(fechaCreacion)
                    //Cambiar a boolean
                    if (disponible == 1) {
                        lugarEncontrado.disponible = true
                    }
                    lugarEncontrado.idPais = idPais
                    arregloLugares.add(lugarEncontrado)
                }
            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return arregloLugares
    }

    //consultar lugares por pais
    fun consultarLugaresPorPais(idPais: Int): ArrayList<LugarTuristico> {
        val scriptConsultarLugares = "SELECT * FROM LUGARTURISTICO WHERE idPais = ?"
        val baseDatosLectura = readableDatabase
        val parametrosConsultaLectura = arrayOf(idPais.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultarLugares,
            parametrosConsultaLectura
        )
        val existeUsuario = resultadoConsultaLectura.moveToFirst()
        val arregloLugares = arrayListOf<LugarTuristico>()
        if (existeUsuario) {
            do {
                val id = resultadoConsultaLectura.getInt(0) // Indice 0
                val nombre = resultadoConsultaLectura.getString(1)
                val costoEntrada = resultadoConsultaLectura.getDouble(2)
                val fechaCreacion = resultadoConsultaLectura.getString(3)
                val disponible = resultadoConsultaLectura.getInt(4)
                val idPais = resultadoConsultaLectura.getInt(5)
                if (id != null) {
                    // llenar el arreglo con un nuevo BEntrenador
                    val lugarEncontrado = LugarTuristico(0, "", 0.0, null, false, 0)
                    lugarEncontrado.idLT = id
                    lugarEncontrado.nombreLT = nombre
                    lugarEncontrado.costoEntrada = costoEntrada
                    //Cambiar a date
                    lugarEncontrado.fechaCreacion = formatoFecha.parse(fechaCreacion)
                    //Cambiar a boolean
                    if (disponible == 1) {
                        lugarEncontrado.disponible = true
                    }
                    lugarEncontrado.idPais = idPais
                    arregloLugares.add(lugarEncontrado)
                }
            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return arregloLugares
    }
}