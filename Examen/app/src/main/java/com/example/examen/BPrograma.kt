package com.example.examen

class BPrograma (
    var id:Int,
    var nombrePrograma:String?,
    var almacenamiento:String?,
    var version:String?,
) {
    override fun toString(): String {
        return "${nombrePrograma} - ${version} - ${almacenamiento}"
    }
}