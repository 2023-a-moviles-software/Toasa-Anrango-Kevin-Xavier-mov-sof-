package com.example.myapplication

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class FRecyclerViewOpciones(
    private val contexto: MainActivity,
    private val listaopciones: ArrayList<String>,
    private val recyclerView: RecyclerView
) : RecyclerView.Adapter<FRecyclerViewOpciones.MyViewHolderOp>() {

    inner class MyViewHolderOp(view: View) : RecyclerView.ViewHolder(view) {
        val nombreOpcion: TextView
        val accionBoton: LinearLayout
        init {
            nombreOpcion = view.findViewById(R.id.tv_opcion)
            accionBoton= view.findViewById(R.id.rv_op)
            accionBoton.setOnClickListener { irActividad(SeleccionarComboBono::class.java)  }
        }

    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolderOp {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.opciones_disponibles,
                parent,
                false
            )
        return MyViewHolderOp(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolderOp, position: Int) {
        val elementoActual = this.listaopciones[position]
        holder.nombreOpcion.text = elementoActual
    }

    override fun getItemCount(): Int {
        return this.listaopciones.size
    }

    private fun irActividad(clase: Class<*>) {
        val intent = Intent(contexto, clase)
        contexto.startActivity(intent)
    }
}

