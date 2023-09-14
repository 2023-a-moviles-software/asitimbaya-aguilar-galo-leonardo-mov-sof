package com.example.exameniib.models

class LugarTuristico (
    var nombreLT: String?,
    var costoEntrada: Number?,
    var disponible: Boolean?,
){
    override fun toString(): String {
        return "${nombreLT} - $ ${costoEntrada} - ${disponible}"
    }
}