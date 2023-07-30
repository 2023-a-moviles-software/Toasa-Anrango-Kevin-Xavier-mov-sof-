package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    val listaOpciones = arrayListOf("Combos", "Bonos")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        inicializarRecyclerView()

    }
    fun  inicializarRecyclerView(){
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewOpciones)
        val adaptador = FRecyclerViewOpciones(
            this,
            listaOpciones,
            recyclerView
        )
        recyclerView.adapter = adaptador
        recyclerView.itemAnimator = androidx.recyclerview.widget
            .DefaultItemAnimator()
        recyclerView.layoutManager = androidx.recyclerview.widget
            .LinearLayoutManager(this)
        adaptador.notifyDataSetChanged()
    }
    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(this, clase)
        // NO RECIBIMOS RESPUESTA
        startActivity(intent)
        // this.startActivity()
    }
}