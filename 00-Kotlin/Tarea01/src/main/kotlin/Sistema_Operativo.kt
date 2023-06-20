class Sistema_Operativo {
    private var nombre_SO: String
    private var nombre_Empresa: String
    private var version: String
    private var ranked: Int
    private var programas: ArrayList<Programa>

    constructor(
        nombre_SO: String,
        nombre_Empresa: String,
        version: String,
        ranked: Int,
        programas: ArrayList<Programa>
    ) {
        this.nombre_SO = nombre_SO
        this.nombre_Empresa = nombre_Empresa
        this.version = version
        this.ranked = ranked
        this.programas = programas
    }

    fun getNombre_SO(): String {
        return nombre_SO
    }

    fun setNombre_SO(nombre_SO: String) {
        this.nombre_SO = nombre_SO
    }

    fun getNombre_Empresa(): String {
        return nombre_Empresa
    }

    fun setNombre_Empresa(nombre_Empresa: String) {
        this.nombre_Empresa = nombre_Empresa
    }

    fun getVersion(): String {
        return version
    }

    fun setVersion(version: String) {
        this.version = version
    }

    fun getRanked(): Int {
        return ranked
    }

    fun setRanked(ranked: Int) {
        this.ranked = ranked
    }

    fun getProgramas(): ArrayList<Programa> {
        return programas
    }

    fun setProgramas(programas: ArrayList<Programa>) {
        this.programas = programas
    }
}
