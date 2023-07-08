package com.example.examen

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog

class BListView : AppCompatActivity() {
    val arreglo = BBaseDatosMemoria.arregloBSistemaOperativo
    val idItemSelecionado = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blist_view)

        //Adaptador personalizado
        val listView = findViewById<ListView>(R.id.lv_list_view)
        val adaptador = object : ArrayAdapter<BSistemaOperativo>(
            this,
            android.R.layout.simple_list_item_1,
            arreglo
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)
                val nombreSO = arreglo[position].nombreSO
                view.findViewById<TextView>(android.R.id.text1).text = nombreSO
                return view
            }
        }
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        val botonAniadirListView = findViewById<Button>(R.id.btn_anadir_list_view)
        botonAniadirListView.setOnClickListener(){
            anadirSO(adaptador)
        }
        registerForContextMenu(listView)
    }
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val position = info.position
        return when(item.itemId){
            R.id.mi_editar ->{
                editarSistemaOperativo(position)
                //Hacer algo con idSeleccionado
                return true
            }
            R.id.mi_eliminar ->{
                abrirDialogo(position)
                //Hacer algo con idSeleccionado
                return true
            }
            R.id.mi_ver_programas -> {
                mostrarProgramas(position)
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
    fun abrirDialogo(position: Int){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Desea elminar?")
        builder.setPositiveButton(
            "Aceptar",
            DialogInterface.OnClickListener(){
                    dialog, which ->  eliminarElemento(position)//Hacer Algo
            }
        )
        builder.setNegativeButton("Cancelar", null)

        val dialogo = builder.create()
        dialogo.show()
    }

    fun eliminarElemento(position: Int) {
        arreglo.removeAt(position)
        val listView = findViewById<ListView>(R.id.lv_list_view)
        val adaptador = listView.adapter as ArrayAdapter<BSistemaOperativo>
        adaptador.notifyDataSetChanged()
    }
    fun anadirSO(adaptador: ArrayAdapter<BSistemaOperativo>){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Nuevo Sistema Operativo")
        val editTextSO= EditText(this)
        editTextSO.setText("")
        builder.setView(editTextSO)
        builder.setPositiveButton("Aceptar") { dialog, which ->
            val nuevoSO = editTextSO.text.toString()
            val nuevoSistemaOperativo = BSistemaOperativo(4, nuevoSO, null)
            BBaseDatosMemoria.arregloBSistemaOperativo.add(nuevoSistemaOperativo)
            val listView = findViewById<ListView>(R.id.lv_list_view)
            val adaptador = listView.adapter as ArrayAdapter<BSistemaOperativo>
            adaptador.notifyDataSetChanged()
        }
        builder.setNegativeButton("Cancelar", null)

        val dialogo = builder.create()
        dialogo.show()
    }


    private fun mostrarProgramas(position: Int) {
        val intent = Intent(this, listaProgramas::class.java)
        intent.putExtra("position", position)
        startActivity(intent)
    }
    private fun editarSistemaOperativo(position: Int) {
        val sistemaOperativo = arreglo[position]

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Editar Sistema Operativo")

        val input = EditText(this)
        input.setText(sistemaOperativo.nombreSO)
        builder.setView(input)

        builder.setPositiveButton("Guardar") { dialog, which ->
            val nuevoNombreSO = input.text.toString()
            sistemaOperativo.nombreSO = nuevoNombreSO
            val listView = findViewById<ListView>(R.id.lv_list_view)
            val adaptador = listView.adapter as ArrayAdapter<BSistemaOperativo>
            adaptador.notifyDataSetChanged()
        }

        builder.setNegativeButton("Cancelar", null)

        val dialogo = builder.create()
        dialogo.show()
    }

}