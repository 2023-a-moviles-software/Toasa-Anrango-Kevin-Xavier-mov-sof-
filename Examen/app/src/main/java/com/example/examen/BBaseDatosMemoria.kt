package com.example.examen899

class BBaseDatosMemoria {

    companion object{
        var arregloBSistemaOperativo = arrayListOf<BSistemaOperativo>()
        val programasWindows = ArrayList<BPrograma>()
        val programasLinux = ArrayList<BPrograma>()
        init {
            programasWindows.add(BPrograma(0,"Visual Studio","150MB","1.0"))
            programasWindows.add(BPrograma(1,"Android Studio","180MB","1.0"))
            arregloBSistemaOperativo.add(BSistemaOperativo(0, "Windows","1.0", "64bits", programasWindows))
            programasLinux.add(BPrograma(0,"SonarQube","160MB","1.0"))
            arregloBSistemaOperativo.add(BSistemaOperativo(1, "Linux","1.0", "32bits", programasLinux))
        }
    }
}