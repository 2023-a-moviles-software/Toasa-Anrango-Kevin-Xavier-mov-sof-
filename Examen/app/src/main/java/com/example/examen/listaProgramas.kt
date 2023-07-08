package com.example.examen

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog

class listaProgramas : AppCompatActivity() {
    lateinit var sistemaOperativo: BSistemaOperativo
    lateinit var programas: ArrayList<String>
    var position = 0
    var positionPrograma = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_programas)

        position = intent.getIntExtra("position", 0)
        sistemaOperativo = BBaseDatosMemoria.arregloBSistemaOperativo[position]
        programas = sistemaOperativo.programas ?: ArrayList()
        val tvNombreSO = findViewById<TextView>(R.id.tv_nombreSO)
        tvNombreSO.text = sistemaOperativo.nombreSO
        val listViewProgramas = findViewById<ListView>(R.id.lv_programas)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            programas
        )

        listViewProgramas.adapter = adaptador
        adaptador.notifyDataSetChanged()
        val botonCrearPrograma = findViewById<Button>(R.id.btn_crear_programa)
        botonCrearPrograma.setOnClickListener(){
            anadirPrograma(adaptador)
        }
        registerForContextMenu(listViewProgramas)
    }



    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_programa, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        positionPrograma = info.position

        return when(item.itemId){
            R.id.mi_editar_programa -> {
                editarPrograma()
                true
            }
            R.id.mi_eliminar_programa -> {
                abrirDialogo()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun abrirDialogo(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿Desea eliminar el programa?")
        builder.setPositiveButton(
            "Aceptar",
            DialogInterface.OnClickListener { dialog, which ->
                eliminarPrograma()
            }
        )
        builder.setNegativeButton("Cancelar", null)

        val dialogo = builder.create()
        dialogo.show()
    }

    fun eliminarPrograma() {
        programas.removeAt(positionPrograma)
        sistemaOperativo.programas = programas
        BBaseDatosMemoria.arregloBSistemaOperativo[position] = sistemaOperativo

        val listViewProgramas = findViewById<ListView>(R.id.lv_programas)
        val adaptador = listViewProgramas.adapter as ArrayAdapter<String>
        adaptador.notifyDataSetChanged()
    }

    fun editarPrograma() {
        val programaActual = programas[positionPrograma]

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Editar programa")
        val editTextPrograma = EditText(this)
        editTextPrograma.setText(programaActual)
        builder.setView(editTextPrograma)
        builder.setPositiveButton("Aceptar") { dialog, which ->
            val nuevoPrograma = editTextPrograma.text.toString()
            programas[positionPrograma] = nuevoPrograma
            sistemaOperativo.programas = programas
            BBaseDatosMemoria.arregloBSistemaOperativo[position] = sistemaOperativo

            val listViewProgramas = findViewById<ListView>(R.id.lv_programas)
            val adaptador = listViewProgramas.adapter as ArrayAdapter<String>
            adaptador.notifyDataSetChanged()
        }
        builder.setNegativeButton("Cancelar", null)

        val dialogo = builder.create()
        dialogo.show()
    }
    fun anadirPrograma(adaptador: ArrayAdapter<String>){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Nuevo Programa")
        val editTextSO= EditText(this)
        editTextSO.setText("")
        builder.setView(editTextSO)
        builder.setPositiveButton("Aceptar") { dialog, which ->
            val nuevoPrograma = editTextSO.text.toString()
            programas.add(nuevoPrograma)
            sistemaOperativo.programas?.add(nuevoPrograma)
            BBaseDatosMemoria.arregloBSistemaOperativo[position] = sistemaOperativo
            adaptador.notifyDataSetChanged()
        }
        builder.setNegativeButton("Cancelar", null)
        val dialogo = builder.create()
        dialogo.show()
    }
}
