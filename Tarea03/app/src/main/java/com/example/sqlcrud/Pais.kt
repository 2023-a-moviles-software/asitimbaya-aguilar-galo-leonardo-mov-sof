package com.example.sqlcrud

class Pais(
    var id: Int?,
    var nombre: String?,
    var idioma: String?,
    var moneda: String?,
    var precioDolar: Double?,
) {
    override fun toString(): String {
        return "${id}|${nombre}|${idioma}|${moneda}|${precioDolar}"
    }
}