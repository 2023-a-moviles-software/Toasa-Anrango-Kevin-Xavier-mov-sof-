package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView

class SeleccionarComboBono : AppCompatActivity() {
    val arreglo = ListaDeElementos.arregloElementos
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seleccionar_combo_bono)
        inicializarRecyclerView()
    }
    fun  inicializarRecyclerView(){
        val recyclerView = findViewById<RecyclerView>(R.id.listRecyclerView)
        val adaptador = FRecyclerViewPlanes(
            this,
            arreglo,
            recyclerView
        )
        recyclerView.adapter = adaptador
        recyclerView.itemAnimator = androidx.recyclerview.widget
            .DefaultItemAnimator()
        recyclerView.layoutManager = androidx.recyclerview.widget
            .LinearLayoutManager(this)
        adaptador.notifyDataSetChanged()
    }
}