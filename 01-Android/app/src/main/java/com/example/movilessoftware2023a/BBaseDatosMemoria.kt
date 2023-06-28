package com.example.movilessoftware2023a

class BBaseDatosMemoria {

    companion object{
        val arregloBEntrenador = arrayListOf<BEntrenador>()
        init {
            arregloBEntrenador
                .add(
                    BEntrenador(1, "Mike", "a@a.com")
                )
            arregloBEntrenador
                .add(
                    BEntrenador(2, "Angelo", "b@b.com")
                )
            arregloBEntrenador
                .add(
                    BEntrenador(3, "Carolina", "c@c.com")
                )
        }
    }
}
