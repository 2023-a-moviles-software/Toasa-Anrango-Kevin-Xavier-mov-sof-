package com.example.examen

class BSistemaOperativo (
    var id:Int,
    var nombreSO:String?,
    var version:String?,
    var distribucion:String?,
    var programas: ArrayList<BPrograma>?
) {
    override fun toString(): String {
        return "${id} - ${nombreSO} - ${version} - ${distribucion} - ${programas}"
    }
}