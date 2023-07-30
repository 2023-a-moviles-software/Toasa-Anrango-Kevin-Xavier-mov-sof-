package com.example.myapplication

class ListaDeElementos {
    companion object{
        val arregloElementos = arrayListOf<Elemento>()
        init {
            arregloElementos
                .add(
                    Elemento("Combo $3 + 5GB GRATIS", "WhatsApp Gratis", "2GB Redes Sociales", "TikTok inlcuido", "min Ilimitados Movistar", "8GB")
                )
            arregloElementos
                .add(
                    Elemento("Combo $8 + 5GB GRATIS", "WhatsApp Gratis", "2GB Redes Sociales","TikTok inlcuido", "min Ilimitados Movistar","100GB")
                )
            arregloElementos
                .add(
                    Elemento("Combo $5 + 5GB GRATIS", "WhatsApp Gratis", "1GB Redes Sociales","TikTok inlcuido", "min ilimitados Movistar","10GB")
                )
        }
    }
}