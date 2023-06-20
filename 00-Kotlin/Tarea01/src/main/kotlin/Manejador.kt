import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File

class Manejador{
    private val SO_FILE = "SO.json"
    private val objectMapper = jacksonObjectMapper()

    fun inicializarInformacion() {
        // Crear un objeto de ejemplo
        val programas = ArrayList<Programa>()

        val programa0 = Programa(
            nombre_programa = "MiPrograma",
            almacenamiento = "10GB"
        )
        programas.add(programa0)

        val programa1 = Programa(
            nombre_programa = "MiPrograma",
            almacenamiento = "10GB"
        )
        programas.add(programa1)


        val sistemaOperativo = Sistema_Operativo(
            nombre_SO = "MiSO",
            nombre_Empresa = "MiEmpresa",
            version = "1.0",
            ranked = 10,
            programas = programas
        )
        crearArchivo()
       insertarInformacion(sistemaOperativo)
    }
    fun insertarInformacion(sistemaOperativo: Sistema_Operativo){
        val archivo = File(SO_FILE)
        // Leer el contenido existente del archivo JSON
        val archivoJson = archivo.readText()
        // Convertir el contenido del archivo en una lista de objetos Sistema_Operativo
        val sistemasOperativos = objectMapper.readValue<List<Sistema_Operativo>>(archivoJson)
        // Crear una nueva lista y agregar los elementos existentes
        val nuevaLista = sistemasOperativos.toMutableList()
        // Agregar el nuevo sistema operativo a la nueva lista
        nuevaLista.add(sistemaOperativo)
        // Guardar la lista actualizada en el archivo JSON
        val nuevoArchivoJson = objectMapper.writeValueAsString(nuevaLista)
        archivo.writeText(nuevoArchivoJson)
        println("Información insertada correctamente.")
    }
    private fun crearArchivo(){
        val archivo = File(SO_FILE)
        // Verificar si el archivo existe
        if (!archivo.exists()) {
            archivo.createNewFile()
            val listaVacia = emptyList<Sistema_Operativo>()
            val nuevoArchivoJson = objectMapper.writeValueAsString(listaVacia)
            archivo.writeText(nuevoArchivoJson)
        }
    }

    fun mostrarContenidoArchivo() {
        val archivo = File(SO_FILE)
        // Verificar si el archivo existe
        if (archivo.exists()) {
            val archivoJson = archivo.readText()
            val sistemasOperativos = objectMapper.readValue<List<Sistema_Operativo>>(archivoJson)

            // Recorrer la lista de sistemas operativos y mostrar la información
            for (sistemaOperativo in sistemasOperativos) {
                println("Nombre del SO: ${sistemaOperativo.getNombre_SO()}")
                println("\t\t Nombre de la empresa: ${sistemaOperativo.getNombre_Empresa()}")
                println("\t\t Versión: ${sistemaOperativo.getVersion()}")
                println("\t\t Ranked: ${sistemaOperativo.getRanked()}")
                println("Programas")

                val programas = sistemaOperativo.getProgramas()
                for (programa in programas) {
                    println("\t\t Nombre del programa: ${programa.getNombre_programa()}")
                    println("\tAlmacenamiento: ${programa.getAlmacenamiento()}")
                }

                println("----------------------------------")
            }
        } else {
            println("El archivo no existe.")
        }
    }

    fun eliminarInformacion(nombreSO: String){
        val archivo = File(SO_FILE)
        val archivoJson = archivo.readText()
        val sistemasOperativos = objectMapper.readValue<List<Sistema_Operativo>>(archivoJson)
        val nuevaLista = sistemasOperativos.toMutableList()
        val sistemasOperativo = nuevaLista.find { it.getNombre_SO() == nombreSO }
        if( sistemasOperativo != null){
            nuevaLista.remove(sistemasOperativo)
        }else{
            println("El sistema operativo ingresado no existe")
        }

        // Guardar la lista actualizada en el archivo JSON
        val nuevoArchivoJson = objectMapper.writeValueAsString(nuevaLista)
        archivo.writeText(nuevoArchivoJson)
        println("Información eliminada correctamente.")
    }

//---------------------------------------------------------------------------------------

    fun imprimirInformacionSistemaOperativo(sistemaOperativo: Sistema_Operativo) {
        println("Información actual del sistema operativo:")
        println("Nombre del SO: ${sistemaOperativo.getNombre_SO()}")
        println("Nombre de la empresa: ${sistemaOperativo.getNombre_Empresa()}")
        println("Versión: ${sistemaOperativo.getVersion()}")
        println("Ranked: ${sistemaOperativo.getRanked()}")

        println("Programas:")
        for ((indice, programa) in sistemaOperativo.getProgramas().withIndex()) {
            println("\tPrograma $indice:")
            println("  \t\tNombre del programa: ${programa.getNombre_programa()}")
            println("  \t\tAlmacenamiento: ${programa.getAlmacenamiento()}")
        }
    }


    fun modificarNombreSO(sistemaOperativo: Sistema_Operativo) {
        println("Ingrese el nuevo nombre del SO:")
        val nuevoNombreSO = readLine()
        sistemaOperativo.setNombre_SO(nuevoNombreSO ?: "")
        println("El nombre del SO se ha actualizado correctamente.")
    }

    fun modificarNombreEmpresa(sistemaOperativo: Sistema_Operativo) {
        println("Ingrese el nuevo nombre de la empresa:")
        val nuevoNombreEmpresa = readLine()
        sistemaOperativo.setNombre_Empresa(nuevoNombreEmpresa ?: "")
        println("El nombre de la empresa se ha actualizado correctamente.")
    }

    fun modificarVersion(sistemaOperativo: Sistema_Operativo) {
        println("Ingrese la nueva versión:")
        val nuevaVersion = readLine()
        sistemaOperativo.setVersion(nuevaVersion ?: "")
        println("La versión se ha actualizado correctamente.")
    }

    fun modificarRanked(sistemaOperativo: Sistema_Operativo) {
        println("Ingrese el nuevo ranked:")
        val nuevoRanked = readLine()?.toIntOrNull()
        if (nuevoRanked != null) {
            sistemaOperativo.setRanked(nuevoRanked)
            println("El ranked se ha actualizado correctamente.")
        } else {
            println("El valor ingresado no es válido.")
        }
    }

    fun agregarPrograma(sistemaOperativo: Sistema_Operativo) {
        println("Ingrese el nombre del programa:")
        val nombrePrograma = readLine()
        println("Ingrese el almacenamiento del programa:")
        val almacenamientoPrograma = readLine()
        val nuevoPrograma = Programa(nombrePrograma ?: "", almacenamientoPrograma ?: "")
        sistemaOperativo.getProgramas().add(nuevoPrograma)
        println("El programa se ha añadido correctamente.")
    }

    fun eliminarPrograma(sistemaOperativo: Sistema_Operativo) {
        println("Ingrese el índice del programa que desea eliminar:")
        val indicePrograma = readLine()?.toIntOrNull()
        if (indicePrograma != null && indicePrograma in 0 until sistemaOperativo.getProgramas().size) {
            sistemaOperativo.getProgramas().removeAt(indicePrograma)
            println("El programa se ha eliminado correctamente.")
        } else {
            println("El índice ingresado no es válido.")
        }
    }

    fun modificarPrograma(sistemaOperativo: Sistema_Operativo) {
        println("Ingrese el índice del programa que desea actualizar:")
        val indicePrograma = readLine()?.toIntOrNull()
        if (indicePrograma != null && indicePrograma in 0 until sistemaOperativo.getProgramas().size) {
            val programa = sistemaOperativo.getProgramas()[indicePrograma]
            println("Información actual del programa:")
            println("Nombre del programa: ${programa.getNombre_programa()}")
            println("Almacenamiento: ${programa.getAlmacenamiento()}")

            println("¿Qué atributo deseas modificar? (nombrePrograma, almacenamiento)")
            val atributoPrograma = readLine()?.toLowerCase()
            when (atributoPrograma) {
                "nombreprograma" -> {
                    println("Ingrese el nuevo nombre del programa:")
                    val nuevoNombrePrograma = readLine()
                    programa.setNombre_programa(nuevoNombrePrograma ?: "")
                    println("El nombre del programa se ha actualizado correctamente.")
                }
                "almacenamiento" -> {
                    println("Ingrese el nuevo almacenamiento:")
                    val nuevoAlmacenamiento = readLine()
                    programa.setAlmacenamiento(nuevoAlmacenamiento ?: "")
                    println("El almacenamiento se ha actualizado correctamente.")
                }
                else -> {
                    println("El atributo ingresado no es válido.")
                }
            }
        } else {
            println("El índice ingresado no es válido.")
        }
    }

    fun actualizarInformacion(nombreSO: String) {
        val archivo = File(SO_FILE)
        val archivoJson = archivo.readText()
        val sistemasOperativos = objectMapper.readValue<List<Sistema_Operativo>>(archivoJson)
        val nuevaLista = sistemasOperativos.toMutableList()
        val sistemaOperativo = nuevaLista.find { it.getNombre_SO() == nombreSO }

        if (sistemaOperativo != null) {
            imprimirInformacionSistemaOperativo(sistemaOperativo)
            println("¿Qué atributo deseas modificar? (nombre_SO, nombre_Empresa, version, ranked, programas)")
            val atributoModificar = readLine()?.toLowerCase()
            when (atributoModificar) {
                "nombre_so" -> modificarNombreSO(sistemaOperativo)
                "nombre_empresa" -> modificarNombreEmpresa(sistemaOperativo)
                "version" -> modificarVersion(sistemaOperativo)
                "ranked" -> modificarRanked(sistemaOperativo)
                "programas" -> {
                    println("¿Qué acción deseas realizar con los programas? (añadir/eliminar/actualizar)")
                    val accionProgramas = readLine()?.toLowerCase()
                    when (accionProgramas) {
                        "añadir" -> agregarPrograma(sistemaOperativo)
                        "eliminar" -> eliminarPrograma(sistemaOperativo)
                        "actualizar" -> modificarPrograma(sistemaOperativo)
                        else -> println("La acción ingresada no es válida.")
                    }
                }
                else -> println("El atributo ingresado no es válido.")
            }

            // Guardar la lista actualizada en el archivo JSON
            val nuevoArchivoJson = objectMapper.writeValueAsString(nuevaLista)
            archivo.writeText(nuevoArchivoJson)
            println("Información actualizada correctamente.")
        } else {
            println("El sistema operativo ingresado no existe.")
        }
    }






}