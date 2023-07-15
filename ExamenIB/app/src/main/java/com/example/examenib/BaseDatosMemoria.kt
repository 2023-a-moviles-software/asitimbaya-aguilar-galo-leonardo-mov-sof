package com.example.examenib

import java.text.SimpleDateFormat

class BaseDatosMemoria {
    companion object {
        val arregloPais = arrayListOf<Pais>()
        val arregloLugarTuristico = arrayListOf<LugarTuristico>()
        val formatoFecha = SimpleDateFormat("dd/MM/yyyy")

        init {
            arregloPais.add(
                Pais(
                    1,
                    "Ecuador",
                    "Español",
                    "Dolar",
                    1.0,
                )
            )
            arregloPais.add(
                Pais(
                    2,
                    "Peru",
                    "Español",
                    "Sol",
                    0.3,
                )
            )
            arregloPais.add(
                Pais(
                    3,
                    "Colombia",
                    "Español",
                    "Peso",
                    0.00027,

                    )
            )
        }

        init {
            arregloLugarTuristico.add(
                LugarTuristico(
                    1,
                    "Galapagos",
                    100.0,
                    formatoFecha.parse("28/12/2020"),
                    false,
                    1,
                )
            )
            arregloLugarTuristico.add(
                LugarTuristico(
                    2,
                    "Quito",
                    50.0,
                    formatoFecha.parse("28/12/2020"),
                    true,
                    1,
                )
            )
            arregloLugarTuristico.add(
                LugarTuristico(
                    3,
                    "Cuenca",
                    20.0,
                    formatoFecha.parse("28/12/2020"),
                    true,
                    1,
                )
            )
            arregloLugarTuristico.add(
                LugarTuristico(
                    4,
                    "Machu Picchu",
                    100.0,
                    formatoFecha.parse("28/12/2020"),
                    true,
                    2,
                )
            )
            arregloLugarTuristico.add(
                LugarTuristico(
                    5,
                    "Lima",
                    50.0,
                    formatoFecha.parse("28/12/2020"),
                    true,
                    2,
                )
            )
            arregloLugarTuristico.add(
                LugarTuristico(
                    6,
                    "Cuzco",
                    20.0,
                    formatoFecha.parse("28/12/2020"),
                    true,
                    2,
                )
            )
            arregloLugarTuristico.add(
                LugarTuristico(
                    7,
                    "Cartagena",
                    100.0,
                    formatoFecha.parse("28/12/2020"),
                    true,
                    3,
                )
            )
            arregloLugarTuristico.add(
                LugarTuristico(
                    8,
                    "Bogota",
                    50.0,
                    formatoFecha.parse("28/12/2020"),
                    true,
                    3,
                )
            )
            arregloLugarTuristico.add(
                LugarTuristico(
                    9,
                    "Medellin",
                    20.0,
                    formatoFecha.parse("28/12/2020"),
                    true,
                    3,
                )
            )
        }
    }
}