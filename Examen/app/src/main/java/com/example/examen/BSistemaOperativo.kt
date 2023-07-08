package com.example.examen

class BSistemaOperativo (
    var id:Int,
    var nombreSO:String?,
    var programas: ArrayList<String>?
) {
    override fun toString(): String {
        return "${id} - ${nombreSO} - ${programas}"
    }
}