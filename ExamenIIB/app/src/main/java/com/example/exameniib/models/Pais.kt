package com.example.exameniib.models

class Pais(
    var nombre: String?,
    var idioma: String?,
    var moneda: String?,
    var precioDolar: Number?,
) {
    override fun toString(): String {
        return "${nombre} | ${idioma} | ${moneda} | ${precioDolar}"
    }
}