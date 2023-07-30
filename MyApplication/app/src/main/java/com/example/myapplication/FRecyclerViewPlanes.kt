package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FRecyclerViewPlanes(
    private val contexto: SeleccionarComboBono,
    private val lista: ArrayList<Elemento>,
    private val recyclerView: RecyclerView
):RecyclerView.Adapter<FRecyclerViewPlanes.MyViewHolder>() {
    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view){
        val nombrePromTextView: TextView
        val whatsappPromTextView: TextView
        val redesPromTextView: TextView
        val tiktokPromTextView: TextView
        val minIlimitadosPromTextView: TextView
        val totalPromPromTextView: TextView

        init {
            nombrePromTextView = view.findViewById(R.id.namePromocion)
            whatsappPromTextView = view.findViewById(R.id.whatsappProm)
            redesPromTextView = view.findViewById(R.id.redesSocialesProm)
            tiktokPromTextView = view.findViewById(R.id.tikTokProm)
            minIlimitadosPromTextView = view.findViewById(R.id.minIlimitadosProm)
            totalPromPromTextView = view.findViewById(R.id.totalProm)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView= LayoutInflater.from(parent.context)
            .inflate(
                R.layout.list_element,
                parent,
                false
            )
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return this.lista.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val elementoActual = this.lista[position]
        holder.totalPromPromTextView.text = elementoActual.promocionTotal
        holder.minIlimitadosPromTextView.text = elementoActual.promocionMinIlimitados
        holder.tiktokPromTextView.text = elementoActual.promocionTikTok
        holder.redesPromTextView.text = elementoActual.promocionRedesSociales
        holder.whatsappPromTextView.text = elementoActual.promocionWhatssApp
        holder.nombrePromTextView.text = elementoActual.nombreCombo

    }

}