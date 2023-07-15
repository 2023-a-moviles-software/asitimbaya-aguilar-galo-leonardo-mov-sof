
fun main(args: Array<String>) {

    //Inicializar arreglo de paisees y lugares turisticos

    val arregloPaises = arrayListOf<Pais>()
    val arregloLugaresTuristicos = arrayListOf<LugarTuristico>()


    //Repetir bucle hasta que escoga la opcion salir

    do {
        // mostrar menu principal
        Menu().show()

        // captura input del usuario
        val opcionPrincipal: String? = readLine()

        when (opcionPrincipal) {
            "1" -> {
                mostrarMenuPais()

            }

            "2" -> {
                mostrarMenuLugaresTuristicos()
            }

            "3" -> {
                println("Gracias por usar mi aplicacion")
            }

            else -> {
                println("Opcion no valida")
            }


        }
    } while (opcionPrincipal != "3")


}

fun mostrarMenuPais() {

    do {
        Menu().showMenuPais()
        val opcionPais: String? = readLine()
        when (opcionPais) {
            "1" -> {
                println("Crear Pais")
                Pais.crearPais()
            }

            "2" -> {
                println("Listar Paises")
                Pais.listarPaises()
            }

            "3" -> {
                println("Actualizar Pais")
                Pais.actualizarPais()
            }

            "4" -> {
                println("Eliminar Pais")
                Pais.eliminarPais()
            }

            "5" -> {
            }

            else -> {
                println("Opcion no valida")
            }
        }
    } while (opcionPais != "5")

}

fun mostrarMenuLugaresTuristicos() {

    do {
        println("Gestion de Lugares Turisticos")
        Menu().showMenuLugarTuristico()
        val opcionLugarTuristico: String? = readLine()
        when (opcionLugarTuristico) {
            "1" -> {
                println("Crear Lugar Turistico")
                LugarTuristico.crearLugarTuristico()
            }

            "2" -> {
                println("Listar Lugares Turisticos")
                LugarTuristico.listarLugaresTuristicos()
            }

            "3" -> {
                println("Actualizar Lugar Turistico")
                LugarTuristico.actualizarLugarTuristico()
            }

            "4" -> {
                println("Eliminar Lugar Turistico")
                LugarTuristico.eliminarLugarTuristico()
            }

            "5" -> {
            }

            else -> {
                println("Opcion no valida")
            }

        }

    }while (opcionLugarTuristico != "5")
}


