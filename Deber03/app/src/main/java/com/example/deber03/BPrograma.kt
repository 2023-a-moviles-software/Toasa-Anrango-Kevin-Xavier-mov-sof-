package com.example.deber03

class BPrograma (
    var idSO:Int,
    var idPrograma: Int,
    var nombrePrograma:String?,
    var almacenamiento:String?,
    var version:String?,
) {
    override fun toString(): String {
        return "${nombrePrograma} - ${version} - ${almacenamiento}"
    }
}