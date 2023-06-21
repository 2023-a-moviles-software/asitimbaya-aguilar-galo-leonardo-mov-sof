class Pais(
    var id: Int?,
    var nombre: String?,
    var idioma: String?,
    var moneda: String?,
    var precioDolar: Double?,
) {


    companion object {

        //Variables
        val archivoPaises = "src/main/resources/paises.txt"
        val arregloPaises: ArrayList<Pais> = arrayListOf()

        //FUNCIONES CRUD
        fun crearPais(): Unit {

            //Leer los datos del pais
            println("Ingrese el id del pais:")
            val idPais: String? = readLine()
            println("Ingrese el nombre del pais:")
            val nombrePais: String? = readLine()
            println("Ingrese el idioma del pais:")
            val idiomaPais: String? = readLine()
            println("Ingrese la moneda del pais:")
            val monedaPais: String? = readLine()
            println("Ingrese el precio del dolar del pais:")
            val precioDolarPais: String? = readLine()

            //crear el objeto pais
            val pais = Pais(
                idPais?.toInt(),
                nombrePais,
                idiomaPais,
                monedaPais,
                precioDolarPais?.toDouble()
            )

            GestorArchivos().escribirArchivo(archivoPaises, pais.toString())

            //Confirmar
            println("Pais creado correctamente")
        }
        fun listarPaises(): Unit {
            //resetear arreglo de paises
            arregloPaises.clear()
            GestorArchivos().leerArchivo(archivoPaises).forEach {
                val datos = it.split(",")
                val pais = Pais(
                    datos[0].toInt(),
                    datos[1],
                    datos[2],
                    datos[3],
                    datos[4].toDouble()
                )
                arregloPaises.add(pais)
            }
            println("Listado de paises")
            arregloPaises.forEach() {
                println(it)
            }
        }

        fun actualizarPais() {
            // listar paises
            listarPaises()

            // capturar id del pais a actualizar
            println("Ingrese el id del pais a actualizar:")
            val idPais: String? = readLine()

            // buscar el pais a actualizar
            val pais = arregloPaises.find { it.id == idPais?.toInt() }

            // mostrar datos del pais a actualizar
            println("Datos del pais a actualizar")
            println(pais)

            // capturar nuevos datos del pais
            println("Ingrese el nuevo nombre del pais:")
            val nombrePais: String? = readLine()
            println("Ingrese el nuevo idioma del pais:")
            val idiomaPais: String? = readLine()
            println("Ingrese la nueva moneda del pais:")
            val monedaPais: String? = readLine()
            println("Ingrese el nuevo precio del dolar del pais:")
            val precioDolarPais: String? = readLine()

            // actualizar datos del pais
            pais?.nombre = nombrePais
            pais?.idioma = idiomaPais
            pais?.moneda = monedaPais
            pais?.precioDolar = precioDolarPais?.toDouble()

            // actualizar archivo paises.txt
            GestorArchivos().actualizarArchivo(archivoPaises, arregloPaises)
            //Confirmar
            println("Pais actualizado")
        }

        fun eliminarPais() {
            // listar paises
            listarPaises()

            // capturar id del pais a eliminar
            println("Ingrese el id del pais a eliminar:")
            val idPais: String? = readLine()

            // buscar el pais a eliminar
            val pais = arregloPaises.find { it.id == idPais?.toInt() }

            // mostrar datos del pais a eliminar
            println("Datos del pais a eliminar")
            println(pais)

            // eliminar pais
            arregloPaises.remove(pais)

            // actualizar archivo paises.txt
            GestorArchivos().actualizarArchivo(archivoPaises, arregloPaises)
            //Confirmar
            println("Pais eliminado")
        }

    }

    override fun toString(): String {
        return id.toString() + "," + nombre + "," + idioma + "," + moneda + "," + precioDolar.toString()
    }


}
