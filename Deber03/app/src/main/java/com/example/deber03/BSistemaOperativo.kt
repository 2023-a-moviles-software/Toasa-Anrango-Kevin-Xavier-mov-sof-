package com.example.deber03

class BSistemaOperativo (
    var id:Int,
    var nombreSO:String?,
    var version:String?,
    var distribucion:String?,
) {
    override fun toString(): String {
        return "${id} - ${nombreSO} - ${version} - ${distribucion}"
    }
}