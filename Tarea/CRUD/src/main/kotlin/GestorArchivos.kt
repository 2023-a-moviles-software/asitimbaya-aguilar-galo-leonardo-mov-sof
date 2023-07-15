import java.io.File

class GestorArchivos {

    fun leerArchivo(nombreArchivo: String): List<String> {
        val bufferedReader = File(nombreArchivo).bufferedReader()
        val lineas = bufferedReader.readLines()
        return lineas
    }

    fun escribirArchivo(nombreArchivo: String, toString: String) {
        File(nombreArchivo).appendText(toString + "\n")
    }

    fun actualizarArchivo(nombreArchivo: String, arreglo: List<Any>) {
        val archivo = File(nombreArchivo)
        archivo.writeText("")
        arreglo.forEach {
            escribirArchivo(nombreArchivo, it.toString())
        }
    }

    fun verificarRelacionUnoAMuchos(nombreArchivo: String, id: Int): Boolean {
        val bufferedReader = File(nombreArchivo).bufferedReader()
        val lineas = bufferedReader.readLines()
        val existeRelacion = lineas.find { it.split(",")[0].toInt() == id }
        return existeRelacion != null
    }
}