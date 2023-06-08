import java.util.Date

fun main(args: Array<String>) {
    println("Hello World!")

    // inmutable (no se reasignan)
    val inmutable:String = "Kevin"
    //inmutable=algo no se lo puede hacer despues, da un error

    //mutable, se puede alterar sy valor (reasignar)
    var mutable:String = "Kevin"
    mutable = "Xavier"

    //Duck typing
    var ejemploVariable = "Kevin Toasa"
    val addEjemplo:Int =12
    ejemploVariable.trim()
    //ejemploVariable = edadEjemplo

    //Variable primitiva
    val nombreProfesor: String = "Adrian Eguez"
    val sueldo: Double = 1.2
    val estadoCivil: Char = 'C'
    val mayorEdad: Boolean = true

    //Clases Java
    val fechaNacimiento:Date = Date()

    //Switch
    val estadoCivilWhen = "C"
    when (estadoCivilWhen){
        ("C") -> {
            println("Casado")
        }
        ("S") -> {
            println("Soltero")
        }
        else -> {
            println("No se sabe")
        }
    }
    val esSoltero = if (estadoCivilWhen == "S") "Si" else "No"
    val coqueteo = if (estadoCivilWhen == "S") "Si" else "No"

    // void -> unit

    fun imprimirNombre(nombre: String): Unit{
        println("Nombre : ${nombre}") //template strings
    }

    fun calcularsueldo(
        sueldo: Double, //requerido
        tasa: Double = 12.00, //Opcional (defecto)
        bonoEspecial: Double? = null, //Opcion null
    ): Double{
        if(bonoEspecial ==null){
            return sueldo * (100/tasa)
        }else{
            return sueldo * (100/tasa) * bonoEspecial
        }
    }


    //ParametrosNombrados
    calcularsueldo(18.00)
    calcularsueldo(10.00,12.00,20.00)
    calcularsueldo(20.00, bonoEspecial = 20.00)
    calcularsueldo(bonoEspecial = 20.00, sueldo = 10.0, tasa = 14.00)

    val sumaUno = Suma(1,1)
    val sumaDos = Suma(1,2)
    val sumatres = Suma(1,3)
    val sumaCuatro = Suma(1,4)
    sumaUno.sumar()
    sumaDos.sumar()
    sumatres.sumar()
    sumaCuatro.sumar()
    println(Suma.pi)
    println(Suma.elevarAlCuadrado(3))
    println(Suma.historialSumas)

    //Arreglos
    //tipos de arreglos
    //arreglo estatico
    val arregloEstatico: Array<Int> = arrayOf<Int>(1,2,3)
    println(arregloEstatico)
    //Arreglo dinamico
    val arregloDinamico: ArrayList<Int> = arrayListOf<Int>(1,2,3,4,5,6,7,8,9,10)


    //operadores -> sirven para los arreglos estaticos

    //for each -> Unit
    //Iterar arreglo

    val respuestaForEach: Unit = arregloDinamico
            .forEach{
                valorActual: Int ->
        println("valor actual: ${valorActual}")
    }
    arregloDinamico.forEach {print(it) // el it significa el elemento iterado
    }

    arregloEstatico
        .forEachIndexed { indice: Int, valorActual: Int ->
            println("Valor ${valorActual} Indice: ${indice}")
        }
    println(respuestaForEach)

    //Map -> Muta el arreglo (cambia el arreglo)
    // 1) Enviamos el nuevo valor de la iteracion
    //2) Nos devuelve es un nuevo arreglo con los valores modificados

    val respuestaMap: List<Double> = arregloDinamico
        .map{valorActual: Int ->
            return@map valorActual.toDouble() + 100.00
        }
    println(respuestaMap)

    val respuestaMapDos = arregloDinamico.map { it + 15 }

    //Filter -> filtrar el arreglo
    //1) revolver una expresion (TRUE o FALSE)
    //2) Nuevo arreglo filtrado
    val respuestaFilter: List<Int> = arregloDinamico
        .filter {valorActual: Int ->
            val mayoresACinco: Boolean = valorActual > 5
            return@filter mayoresACinco
        }
    val respuestaFilterDos = arregloDinamico.filter { it <= 5 }
    println(respuestaFilter)
    println(respuestaFilterDos)

    //OR AND
    //OR -> ANY (ALGUNO CUMPLE?)
    //AND -> ALL (TODOS CUMPLEN?)
    val respuestaAny: Boolean  = arregloDinamico
        .any {
            valorActual: Int ->
            return@any (valorActual > 5)
        }
    println(respuestaAny) //true

    val respuestaAll: Boolean  = arregloDinamico
        .all {
                valorActual: Int ->
            return@all (valorActual > 5)
        }
    println(respuestaAll) //false

    //REDUCE -> valor acumulado
    //Valor acumulado = 0 (siempre 0 en lenguaje Kotlin)
    // [1, 2, 3, 4, 5] -> Sumumu todos los valores del arreglo
    // valorIteracion1 = valorEmpiezo +1 = 0 + 1 = 1 --> iteracion1
    //valorIteracion2 = valorIteracion1 +1 = 1 + 1 = 2 --> iteracion2

    val respuestaReduce: Int = arregloDinamico
        .reduce{ //acumulada  -> siempre empieza desde el cero
            acumulado: Int, valorActual: Int ->
            return@reduce (acumulado +valorActual)
        }
    println(respuestaReduce)
}



abstract class NumerosJava{
        protected val numeroUno: Int
        private val numeroDos: Int

        constructor(uno: Int, dos: Int){
            this.numeroUno = uno
            this.numeroDos = dos
            println("Incicializando")
        }

}

abstract class Numeros(
    //ejemplo
    //uno: int (parametro(sin modificador de acceso))
    //private var uno: int //propiedad publica clase numeros.uno
    //var uno: int //propiedad de la clase (por defecto es public)
    //public var uno: int
    protected val numeroUno: Int,
    protected val numeroDos: Int
){
    //var cedula: string = "" {public es por defecto}
    //private valorcalculado: int = 0 {private}
    init{
        this.numeroUno;
        this.numeroDos;
        numeroUno;
        numeroDos;
        println("Inicializando")

    }
}

class Suma( //Constructor primario suma
    unoParametro: Int, //parametro
    dosParametro: Int //parametro
):Numeros(unoParametro,dosParametro){ //extendiendo y mandado los paramtros super
    init{ //bloque codigo constructori primario
        this.numeroUno
        this.numeroDos
    }
    constructor(
     uno: Int?,
    dos: Int
    ):this(
        if(uno == null) 0 else uno,
        dos
    )

    constructor(
        uno: Int,
        dos: Int?
    ):this(
        uno,
        if(dos == null) 0 else dos
    )

    constructor(
        uno: Int?,
        dos: Int?
    ):this(
        if(uno == null) 0 else uno,
        if(dos == null) 0 else dos
    )

    //metodos
    public fun sumar(): Int{
        val total = numeroUno + numeroDos
        agregarHistorial(total)
        return total
    }

    companion object{ // atributos y metodos "compartidos" singletons o
        //todos las instancias d eesta clase comparten estos atributos
        //dentro del companion object
        val pi = 3.14
        fun elevarAlCuadrado(num: Int): Int{
            return num*num
        }
        val historialSumas = ArrayList<Int>()
        fun agregarHistorial (valorNuevaSuma: Int){
            historialSumas.add(valorNuevaSuma)
        }
    }
}