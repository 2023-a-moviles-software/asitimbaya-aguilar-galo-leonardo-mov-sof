package com.example.autoassist.model

class Mantenimiento(
    val id: String? = null,
    val tipoMantenimiento: String? = null,
    val kmActual: String? = null,
    val fechaMantenimiento: String? = null,
    val nombreMecanica: String? = null,
    val detalle: String? = null,
) {
    override fun toString(): String {
        return  "${tipoMantenimiento} - ${fechaMantenimiento}"
    }
}