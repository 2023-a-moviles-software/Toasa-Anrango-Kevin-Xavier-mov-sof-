class Programa {
    private var nombre_programa: String
    private var almacenamiento: String

    constructor(
        nombre_programa: String,
        almacenamiento: String
    ) {
        this.nombre_programa = nombre_programa
        this.almacenamiento = almacenamiento
    }

    fun getNombre_programa(): String {
        return nombre_programa
    }

    fun setNombre_programa(nombre_programa: String) {
        this.nombre_programa = nombre_programa
    }

    fun getAlmacenamiento(): String {
        return almacenamiento
    }

    fun setAlmacenamiento(almacenamiento: String) {
        this.almacenamiento = almacenamiento
    }
}