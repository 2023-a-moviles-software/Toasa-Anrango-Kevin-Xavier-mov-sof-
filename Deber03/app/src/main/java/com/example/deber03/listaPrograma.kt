package com.example.deber03

class listaPrograma {
    companion object{
        var arregloProgramas = arrayListOf<BPrograma>()
        init {
            arregloProgramas.add(BPrograma(1,1,"Visual Studio","150MB","1.6"))
            arregloProgramas.add(BPrograma(1,2,"Android Studio","180MB","1.1"))
            arregloProgramas.add(BPrograma(2,1,"SonarQube","160MB","1.3"))
            arregloProgramas.add(BPrograma(2,2,"Python","16MB","1.1"))
        }
    }
}