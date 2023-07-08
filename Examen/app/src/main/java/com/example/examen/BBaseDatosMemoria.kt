package com.example.examen

class BBaseDatosMemoria {

    companion object{
        var arregloBSistemaOperativo = arrayListOf<BSistemaOperativo>()
        val programasWindows = ArrayList<String>()
        val programasLinux = ArrayList<String>()
        init {
            programasWindows.add("VisualStudio")
            programasWindows.add("Android Studio")
            arregloBSistemaOperativo.add(BSistemaOperativo(0, "Windows", programasWindows))
            programasLinux.add("SonarQube")
            arregloBSistemaOperativo.add(BSistemaOperativo(1, "Linux", programasLinux))
        }
    }
}