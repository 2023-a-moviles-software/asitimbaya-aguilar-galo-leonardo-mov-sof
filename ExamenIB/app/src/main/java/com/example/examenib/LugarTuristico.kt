package com.example.examenib

import java.text.SimpleDateFormat
import java.util.Date

class LugarTuristico (
    var idLT: Int?,
    var nombreLT: String?,
    var costoEntrada: Double?,
    var fechaCreacion: Date?,
    var disponible: Boolean?,
    var idPais: Int?
){
    override fun toString(): String {
        val formatoFecha = SimpleDateFormat("dd/MM/yyyy")
        val fecha = formatoFecha.format(fechaCreacion)
        return "${idLT}-${nombreLT} - $ ${costoEntrada} - ${fecha}- ${disponible}"
    }
}