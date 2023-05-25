import java.util.Date

fun main(args: Array<String>) {
    println("Hello World!")

    //INMUTABLES (NO se reasignan "=")
    val inmutable: String = "Leonardo";

    //Mutables (Re asignar)
    var mutable: String = "Galo";
    mutable = "Leonardo";

    //val > var
    // Duck Typing
    var ejemploVariable = "Leonardo Asitimbaya"
    val edadEjemplo: Int = 12
    ejemploVariable.trim()
    //ejemploVariable = edadEjemplo;

    //Variable primitiva
    val nombreProfesor: String = "Adrian Eguez"
    val sueldo: Double = 1.2
    val estadoCivil: Char = 'S'
    val mayorEdad: Boolean = true
    //Clases Java
    val fechaNacimiento: Date = Date()

    //SWITCH
    val estadoCivilWhen = "C"
    when (estadoCivilWhen) {
        ("C") -> {
            println("Casado")
        }
        "S" -> {
            println("Soltero")
        }
        else -> {
            println("No sabemos")
        }
    }
    val esSoltero = (estadoCivilWhen == "S")
    val coqueteo = if (esSoltero) "Si" else "No"

    //void -> Unit
    fun imprimirNombre(nombre: String): Unit {
        println("Nombre: ${nombre}")//template strings
    }

    fun calcularSueldo(
        sueldo: Double, //Requerido
        tasa: Double = 12.00, //Opcional (defecto)
        bonoEspecial: Double? = null, //Opcion null ->nullable
    ): Double {
        //Int -> Int? (nullable)
        //String -> String? (nullable)
        //Date -> Date? (nullable)
        if (bonoEspecial == null){
            return sueldo * (100/tasa)
        }else{
            return sueldo * (100/tasa) * bonoEspecial
        }
    }
    calcularSueldo(10.00)
    calcularSueldo(10.00, 12.00, 20.00)
    calcularSueldo(10.00, bonoEspecial = 20.00) //Named Parameters
    calcularSueldo(bonoEspecial = 20.00, sueldo = 10.00, tasa = 14.00)
}

abstract class NumerosJava{
    protected val numeroUno: Int
    private val numeroDos: Int
    constructor(
        uno: Int,
        dos: Int
    ){ //Bloque de código del constructor
        this.numeroUno = uno
        this.numeroDos = dos
        println("Inicializando")
    }
}

abstract class Numeros(//Constructor PRIMARIO
// Ejemplo:
//uno: Int, (Parametro (sin modificador de acceso))
// private var uno: Int, // Propiedad pública Clase numeros.uno
//var.uno: Int, //Propiedad de la clase (por defecto es Public)
//public var uno: Int,
protected val numeroUno: Int,
protected val numeroDos: Int,
){
    //var cedula: string = "" (public es por defecto)
    //private valorCalculado: Int = 0 (private)

    init {
        this.numeroUno; this.numeroDos; //this es opcional
        numeroUno; numeroDos; // sin el "this", es lo mismo
        println("Inicializando")
    }
}


