package com.example.deber03

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ESqliteHelper(contexto: Context?,): SQLiteOpenHelper(
    contexto,
    "CRUD", // nombre bdd
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaSistemaOperativo =
            """
                CREATE TABLE IF NOT EXISTS SISTEMA_OPERATIVO(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nombreSO VARCHAR(15),
                    version VARCHAR(5),
                    distribucion VARCHAR(5)
                )
            """.trimIndent()
        val scriptSQLCrearTablaPrograma =
            """
        CREATE TABLE IF NOT EXISTS PROGRAMA (
            idPR INTEGER PRIMARY KEY AUTOINCREMENT,
            idSO INTEGER,
            nombrePrograma VARCHAR(15),
            almacenamiento VARCHAR(5),
            version VARCHAR(5),
            FOREIGN KEY (idSO) REFERENCES SISTEMA_OPERATIVO(id) ON DELETE CASCADE ON UPDATE CASCADE
        )
        """.trimIndent()
        db?.apply{
            execSQL(scriptSQLCrearTablaSistemaOperativo)
            execSQL(scriptSQLCrearTablaPrograma)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?,
                           oldVersion: Int,
                           newVersion: Int) {
        TODO("Not yet implemented")
    }
    fun crearSistemaOperativo(nombreSO: String, version: String, distribucion: String): Boolean {
        val baseDatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombreSO", nombreSO)
        valoresAGuardar.put("version", version)
        valoresAGuardar.put("distribucion", distribucion)
        val resultadoGuardar = baseDatosEscritura
            .insert(
                "SISTEMA_OPERATIVO", // nombre tabla
                null,
                valoresAGuardar, // valores
            )
        baseDatosEscritura.close()
        return if (resultadoGuardar.toInt() === -1) false else true
    }
    fun crearPrograma(idSO: Int, nombrePrograma: String, almacenamiento: String, version: String): Boolean {
        val baseDatosEscrituraPR = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("idSO", idSO)
        valoresAGuardar.put("nombrePrograma", nombrePrograma)
        valoresAGuardar.put("almacenamiento", almacenamiento)
        valoresAGuardar.put("version", version)
        val resultadoGuardar = baseDatosEscrituraPR
            .insert(
                "PROGRAMA", // nombre tabla
                null,
                valoresAGuardar, // valores
            )
        baseDatosEscrituraPR.close()
        return if (resultadoGuardar.toInt() === -1) false else true
    }

    fun consultarTablaSistemaOperativo(): List<BSistemaOperativo> {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
            SELECT * FROM SISTEMA_OPERATIVO
        """.trimIndent()

        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura,
            null // No se requieren parámetros en esta consulta
        )

        val listaSistemaOperativo = mutableListOf<BSistemaOperativo>()

        if (resultadoConsultaLectura.moveToFirst()) {
            do {
                val id = resultadoConsultaLectura.getInt(0)
                val nombreSO = resultadoConsultaLectura.getString(1)
                val version = resultadoConsultaLectura.getString(2)
                val distribucion = resultadoConsultaLectura.getString(3)
                val sistemaOperativo = BSistemaOperativo(id, nombreSO, version, distribucion)
                listaSistemaOperativo.add(sistemaOperativo)
            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return listaSistemaOperativo
    }

    fun eliminarSOFormulario(id: Int): Boolean {
        val conexionEscritura = writableDatabase
        // where ID = ?
        val parametrosConsultaDelete = arrayOf(id.toString())
        val resultadoEliminacion = conexionEscritura
            .delete(
                "SISTEMA_OPERATIVO", // Nombre tabla
                "id=?", // Consulta Where
                parametrosConsultaDelete
            )
        conexionEscritura.close()
        return if (resultadoEliminacion.toInt() == -1) false else true
    }

    fun eliminarProgramaFormulario(id: Int, idSO: Int): Boolean {
        val conexionEscritura = writableDatabase
        // where ID = ? AND idSO = ?
        val parametrosConsultaDelete = arrayOf(id.toString(), idSO.toString())
        val resultadoEliminacion = conexionEscritura
            .delete(
                "PROGRAMA", // Nombre tabla
                "idPR=? AND idSO=?", // Consulta Where con dos condiciones
                parametrosConsultaDelete
            )
        conexionEscritura.close()
        return resultadoEliminacion != -1
    }

    fun consultarProgramasPorIdSistemaOperativo(idSistemaOperativo: Int): List<BPrograma> {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
            SELECT * FROM PROGRAMA WHERE idSO = ?
        """.trimIndent()

        val parametrosConsultaLectura = arrayOf(idSistemaOperativo.toString())

        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura,
            parametrosConsultaLectura
        )

        val listaProgramas = mutableListOf<BPrograma>()

        if (resultadoConsultaLectura.moveToFirst()) {
            do {
                val idPrograma = resultadoConsultaLectura.getInt(0)
                val idSO = resultadoConsultaLectura.getInt(1)
                val nombrePrograma = resultadoConsultaLectura.getString(2)
                val almacenamiento = resultadoConsultaLectura.getString(3)
                val version = resultadoConsultaLectura.getString(4)

                val programa = BPrograma(idSO, idPrograma, nombrePrograma, almacenamiento, version)
                listaProgramas.add(programa)
            } while (resultadoConsultaLectura.moveToNext())
        }

        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return listaProgramas
    }


    fun actualizarSOFormulario(
        id: Int, nombreSO: String, version: String, distribucion: String
    ): Boolean {
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombreSO", nombreSO)
        valoresAActualizar.put("version", version)
        valoresAActualizar.put("distribucion", distribucion)
        // where ID = ?
        val parametrosConsultaActualizar = arrayOf(id.toString())
        val resultadoActualizacion = conexionEscritura
            .update(
                "SISTEMA_OPERATIVO", // Nombre tabla
                valoresAActualizar, // Valores
                "id=?", // Consulta Where
                parametrosConsultaActualizar
            )
        conexionEscritura.close()
        return if (resultadoActualizacion.toInt() == -1) false else true
    }

    fun actualizarProgramaFormulario(idPrograma: Int, idSO: Int, nombrePrograma: String, version: String, almacenamiento: String): Boolean {
        val conexionEscrituraPR = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombrePrograma", nombrePrograma)
        valoresAActualizar.put("almacenamiento", almacenamiento)
        valoresAActualizar.put("version", version)
        // where ID = ? AND idSO = ?
        val parametrosConsultaActualizar = arrayOf(idPrograma.toString(), idSO.toString())
        val resultadoActualizacion = conexionEscrituraPR
            .update(
                "PROGRAMA", // Nombre tabla
                valoresAActualizar, // Valores
                "idPR=? AND idSO=?", // Consulta Where
                parametrosConsultaActualizar
            )
        conexionEscrituraPR.close()
        return resultadoActualizacion != -1
    }

}