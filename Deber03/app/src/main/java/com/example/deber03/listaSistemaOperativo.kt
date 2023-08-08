package com.example.deber03

class listaSistemaOperativo {
    companion object{
        var arregloSistemaOperativo = arrayListOf<BSistemaOperativo>()
        init {
            arregloSistemaOperativo.add(BSistemaOperativo(1, "Windows","1.0", "64bits"))
            arregloSistemaOperativo.add(BSistemaOperativo(2, "Linux","1.0", "32bits"))
        }
    }
}