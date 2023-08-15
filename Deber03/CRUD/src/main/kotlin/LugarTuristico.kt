import java.text.SimpleDateFormat
import java.util.Date
import java.io.File

class LugarTuristico(
    var idLT: Int?,
    var nombreLT: String?,
    var costoEntrada: Double?,
    var fechaCreacion: Date?,
    var disponible: Boolean?,
    var idPais: Int?
) {

    companion object {
        //Variables
        val archivoLT = "src/main/resources/lugares.txt"
        val archivoPaises = "src/main/resources/paises.txt"
        val arregloLT: ArrayList<LugarTuristico> = arrayListOf()
        val formatoFecha = SimpleDateFormat("dd/MM/yyyy")

        //FUNCIONES CRUD
        fun crearLugarTuristico(): Unit {

            //Leer los datos del lugar turistico
            println("Ingrese el id del lugar turistico:")
            val idLT: String? = readLine()
            println("Ingrese el nombre del lugar turistico:")
            val nombreLT: String? = readLine()
            println("Ingrese el costo de entrada del lugar turistico:")
            val costoEntrada: String? = readLine()
            println("Ingrese la fecha de creacion del lugar turistico:")
            val fechaCreacion: String? = readLine()
            println("Ingrese si el lugar turistico esta disponible(true/false):")
            val disponible: String? = readLine()
            println("Ingrese el id del pais del lugar turistico:")
            val idPais: String? = readLine()

            //verificar relacion uno a muchos
            val existeRelacion = GestorArchivos().verificarRelacionUnoAMuchos(archivoPaises, idPais?.toInt()!!)

            if (!existeRelacion) {
                println("No se puede crear el lugar turistico porque el pais no existe")
            }else{
                //crear el objeto lugar turistico
                val lugarTuristico = LugarTuristico(
                    idLT?.toInt(),
                    nombreLT,
                    costoEntrada?.toDouble(),
                    formatoFecha.parse(fechaCreacion),
                    disponible?.toBoolean(),
                    idPais?.toInt()
                )

                GestorArchivos().escribirArchivo(archivoLT, lugarTuristico.toString())

                //Confirmar
                println("Lugar turistico creado correctamente")
            }

        }

        fun listarLugaresTuristicos(): Unit {
            //resetear arreglo de lugares turisticos
            arregloLT.clear()
            GestorArchivos().leerArchivo(archivoLT).forEach {
                val datos = it.split(",")
                val lugarTuristico = LugarTuristico(
                    datos[0].toInt(),
                    datos[1],
                    datos[2].toDouble(),
                    formatoFecha.parse(datos[3]),
                    datos[4].toBoolean(),
                    datos[5].toInt()
                )
                arregloLT.add(lugarTuristico)
            }
            println("Lista de lugares turisticos:")
            arregloLT.forEach {
                println(it)
            }
        }

        fun actualizarLugarTuristico() {
            //listar lugares turisticos
            listarLugaresTuristicos()
            //capturar id del lugar turistico a actualizar
            println("Ingrese el id del lugar turistico a actualizar:")
            val idLT: String? = readLine()

            //buscar el lugar turistico
            val lugarTuristico = arregloLT.find { it.idLT == idLT?.toInt() }

            //mostrar datos del lugar turistico
            println("Datos del lugar turistico:")
            println(lugarTuristico)

            //capturar nuevos datos del lugar turistico
            println("Ingrese el nuevo nombre del lugar turistico:")
            val nombreLT: String? = readLine()
            println("Ingrese el nuevo costo de entrada del lugar turistico:")
            val costoEntrada: String? = readLine()
            println("Ingrese la nueva fecha de creacion del lugar turistico:")
            val fechaCreacion: String? = readLine()
            println("Ingrese si el lugar turistico esta disponible(true/false):")
            val disponible: String? = readLine()
            println("Ingrese el nuevo id del pais del lugar turistico:")
            val idPais: String? = readLine()

            //actualizar datos del lugar turistico
            lugarTuristico?.nombreLT = nombreLT
            lugarTuristico?.costoEntrada = costoEntrada?.toDouble()
            lugarTuristico?.fechaCreacion = formatoFecha.parse(fechaCreacion)
            lugarTuristico?.disponible = disponible?.toBoolean()
            lugarTuristico?.idPais = idPais?.toInt()

            //actualizar archivo
            GestorArchivos().actualizarArchivo(archivoLT, arregloLT)
            //Confirmar
            println("Lugar turistico actualizado correctamente")
        }

        fun eliminarLugarTuristico() {
            //listar lugares turisticos
            listarLugaresTuristicos()

            //capturar id del lugar turistico a eliminar
            println("Ingrese el id del lugar turistico a eliminar:")
            val idLT: String? = readLine()

            //buscar el lugar turistico
            val lugarTuristico = arregloLT.find { it.idLT == idLT?.toInt() }

            //mostrar datos del lugar turistico
            println("Datos del lugar turistico:")
            println(lugarTuristico)

            //eliminar el lugar turistico
            arregloLT.remove(lugarTuristico)

            //actualizar archivo
            GestorArchivos().actualizarArchivo(archivoLT, arregloLT)

            //Confirmar
            println("Lugar turistico eliminado correctamente")
        }
    }

    override fun toString(): String {
        return idLT.toString() + "," +
                nombreLT + "," +
                costoEntrada.toString() + "," +
                formatoFecha.format(fechaCreacion) + "," +
                disponible.toString() + "," +
                idPais.toString()
    }
}