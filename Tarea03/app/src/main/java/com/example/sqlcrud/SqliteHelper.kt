package com.example.sqlcrud

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.text.SimpleDateFormat

class SqliteHelper(
    contexto: Context?, // this
) : SQLiteOpenHelper(
    contexto,
    "tareasql", // nombre bdd
    null,
    5
) {
    val formatoFecha = SimpleDateFormat("dd/MM/yyyy")

    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaPais =
            """
                CREATE TABLE PAIS(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nombre VARCHAR(50),
                    idioma VARCHAR(50),
                    moneda VARCHAR(50),
                    precioDolar DOUBLE
                )
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaPais)

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

    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int
    ) {
    }


    fun crearPais(
        nombre: String,
        idioma: String,
        moneda: String,
        precioDolar: Double

    ): Boolean {
        val baseDatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("idioma", idioma)
        valoresAGuardar.put("moneda", moneda)
        valoresAGuardar.put("precioDolar", precioDolar)
        val resultadoGuardar = baseDatosEscritura
            .insert(
                "PAIS", // nombre tabla
                null,
                valoresAGuardar, // valores
            )
        baseDatosEscritura.close()
        return if (resultadoGuardar.toInt() === -1) false else true
    }

    fun eliminarPaisFormulario(id: Int): Boolean {
        val conexionEscritura = writableDatabase
        // where ID = ?
        val parametrosConsultaDelete = arrayOf(id.toString())
        val resultadoEliminacion = conexionEscritura
            .delete(
                "PAIS", // Nombre tabla
                "id=?", // Consulta Where
                parametrosConsultaDelete
            )
        conexionEscritura.close()
        return if (resultadoEliminacion.toInt() == -1) false else true
    }


    fun actualizarPaisFormulario(
        nombre: String,
        idioma: String,
        moneda: String,
        precioDolar: Double,
        id: Int,
    ): Boolean {
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre", nombre)
        valoresAActualizar.put("idioma", idioma)
        valoresAActualizar.put("moneda", moneda)
        valoresAActualizar.put("precioDolar", precioDolar)
        // where ID = ?
        val parametrosConsultaActualizar = arrayOf(id.toString())
        val resultadoActualizacion = conexionEscritura
            .update(
                "PAIS", // Nombre tabla
                valoresAActualizar, // Valores
                "id=?", // Consulta Where
                parametrosConsultaActualizar
            )
        conexionEscritura.close()
        return if (resultadoActualizacion.toInt() == -1) false else true
    }


    fun consultarPaisPorID(id: Int): Pais {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
            SELECT * FROM PAIS WHERE ID = ?
        """.trimIndent()
        val parametrosConsultaLectura = arrayOf(id.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura, // Consulta
            parametrosConsultaLectura, // Parametros
        )
        // logica busqueda
        val existeUsuario = resultadoConsultaLectura.moveToFirst()
        val usuarioEncontrado = Pais(0, "", "", "", 0.0)
        val arreglo = arrayListOf<Pais>()
        if (existeUsuario) {
            do {
                val id = resultadoConsultaLectura.getInt(0) // Indice 0
                val nombre = resultadoConsultaLectura.getString(1)
                val idioma = resultadoConsultaLectura.getString(2)
                val moneda = resultadoConsultaLectura.getString(3)
                val precioDolar = resultadoConsultaLectura.getDouble(4)
                if (id != null) {
                    // llenar el arreglo con un nuevo BEntrenador
                    usuarioEncontrado.id = id
                    usuarioEncontrado.nombre = nombre
                    usuarioEncontrado.idioma = idioma
                    usuarioEncontrado.moneda = moneda
                    usuarioEncontrado.precioDolar = precioDolar
                }
            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return usuarioEncontrado
    }

    //listar paises


    fun consultarPaises(): ArrayList<Pais> {
        val scriptConsultarPaises = "SELECT * FROM PAIS"
        val baseDatosLectura = readableDatabase
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultarPaises,
            null
        )
        val existePais = resultadoConsultaLectura.moveToFirst()
        val arregloPais = arrayListOf<Pais>()
        if (existePais) {
            do {
                val id = resultadoConsultaLectura.getInt(0)
                val nombre = resultadoConsultaLectura.getString(1)
                val idioma = resultadoConsultaLectura.getString(2)
                val moneda = resultadoConsultaLectura.getString(3)
                val precioDolar = resultadoConsultaLectura.getDouble(4)
                arregloPais.add(
                    Pais(
                        id,
                        nombre,
                        idioma,
                        moneda,
                        precioDolar
                    )
                )
            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return arregloPais
    }


    //LUGAR TURISTICO

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