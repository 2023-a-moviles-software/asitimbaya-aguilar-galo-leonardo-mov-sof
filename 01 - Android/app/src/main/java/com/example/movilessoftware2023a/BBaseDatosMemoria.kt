package com.example.movilessoftware2023a

class BBaseDatosMemoria {
    companion object {
        val arregloBEntrenador = arrayListOf<BEntrenador>()
        init {
            arregloBEntrenador
                .add(
                    BEntrenador(1, "Leonardo", "l@l.com")
                )
            arregloBEntrenador
                .add(
                    BEntrenador(2, "Galo", "g@g.com")
                )
            arregloBEntrenador
                .add(
                    BEntrenador(3, "Peter", "p@p.com")
                )
        }
    }
}