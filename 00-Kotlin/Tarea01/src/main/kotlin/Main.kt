import java.io.File

fun main() {
    val manejador = Manejador()
    manejador.inicializarInformacion()
    var opcion: Int

    do {
        println("---- MENÚ PRINCIPAL ----")
        println("1. Mostrar información")
        println("2. Añadir un nuevo registro")
        println("3. Eliminar un registro")
        println("4. Actualizar información")
        println("0. Salir")
        print("Ingrese una opción: ")
        opcion = readLine()?.toIntOrNull() ?: -1

        when (opcion) {
            1 -> manejador.mostrarContenidoArchivo()
            2 -> { manejador.insertarInformacion(agregarSO())

            }
            3 -> {
                println("Ingrese el nombre del sistema operativo a eliminar:")
                val sistemaEliminado = readLine()
                manejador.eliminarInformacion(sistemaEliminado.toString())
            }
            4 -> {
                println("Ingrese el nombre del sistema operativo a modificar:")
                val sistemaActualizar = readLine()
                manejador.actualizarInformacion(sistemaActualizar.toString())
            }
            0 ->  println("Saliendo del programa...")
            else -> println("Opción inválida. Por favor, ingrese una opción válida.")
        }

        println()
    } while (opcion != 0)
}
    fun agregarSO(): Sistema_Operativo{
        val programas = ArrayList<Programa>()
        println("Ingrese el nombre del Sistema Operativo:")
        val nombreSO = readLine()
        println("Ingrese el nombre de la empresa:")
        val nameEmpresa = readLine()
        println("Ingrese la version:")
        val version = readLine()
        println("En que posicion en el ranked se encuentra el sistema operativo:")
        val ranked = readLine()?.toIntOrNull() ?: 0

        var opcionSubMenu: Int
        do {
            println("---- MENÚ AÑADIR REGISTRO ----")
            println("1. Agregar un programa")
            println("2. Finalizar")
            print("Ingrese una opción: ")
            opcionSubMenu = readLine()?.toIntOrNull() ?: -1

            when (opcionSubMenu) {
                1 -> {
                    println("Ingrese el nombre del programa:")
                    val nombreProgram = readLine()
                    println("Ingrese el peso:")
                    val peso = readLine()
                        programas.add(Programa(
                            nombre_programa = nombreProgram.toString(),
                            almacenamiento = peso.toString()
                        ))
                }
            }
        } while (opcionSubMenu != 2)
        val sistemaOperativo = Sistema_Operativo(
            nombre_SO = nombreSO.toString(),
            nombre_Empresa = nameEmpresa.toString(),
            version = version.toString(),
            ranked = ranked,
            programas = programas
        )

        return sistemaOperativo
    }





