package com.example.deber03

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog

class listaProgramas : AppCompatActivity() {
    var idSO = 0
    var datosSistemaOperativo = EBaseDatos.tabla_SO_PR?.consultarTablaSistemaOperativo()

    val callbackContenidoIntentExplicitoPrograma =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
                result ->
            if(result.resultCode == Activity.RESULT_OK){
                if(result.data != null){
                    //Lógica de negocio
                    val data = result.data
                    val nuevoNombrePrograma = "${data?.getStringExtra("nuevoNombrePr")}"
                    val nuevaVersionPrograma = "${data?.getStringExtra("nuevoVersionPr")}"
                    val nuevaAlmacenamientoPrograma = "${data?.getStringExtra("nuevoAlmacenamientoPr")}"
                    // Crear el nuevo programa
                    EBaseDatos.tabla_SO_PR?.crearPrograma(idSO,nuevoNombrePrograma, nuevaAlmacenamientoPrograma, nuevaAlmacenamientoPrograma)
                    // Actualizar la lista de programas
                    val listViewProgramas = findViewById<ListView>(R.id.lv_programas)
                    val adaptador = listViewProgramas.adapter as ArrayAdapter<BPrograma>
                    adaptador.clear()
                    val nuevalistaProgramas = EBaseDatos.tabla_SO_PR?.consultarProgramasPorIdSistemaOperativo(idSO)
                    if (nuevalistaProgramas != null){
                        adaptador.addAll(nuevalistaProgramas)
                    }
                    adaptador.notifyDataSetChanged()
                }
            }
        }
    val callbackContenidoIntentExplicitoEditarPrograma =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                if (result.data != null) {
                    val data = result.data
                    val nuevoNombrePr = data?.getStringExtra("nuevoNombreSO")
                    val nuevaVersionPr = data?.getStringExtra("nuevoVersionSO")
                    val nuevoAlmacenamiento = data?.getStringExtra("nuevoAlmacenamiento")
                    val idSO = data?.getIntExtra("idSO", -1)
                    val idPr = data?.getIntExtra("idPR", -1)

                    if (idPr != null && idSO != null && nuevoNombrePr != null && nuevaVersionPr != null && nuevoAlmacenamiento != null) {
                        EBaseDatos.tabla_SO_PR?.actualizarProgramaFormulario(idPr, idSO, nuevoNombrePr, nuevaVersionPr, nuevoAlmacenamiento)
                        //Actualizar base de datoss
                        val listViewProgramas = findViewById<ListView>(R.id.lv_programas)
                        val adaptador = listViewProgramas.adapter as ArrayAdapter<BPrograma>
                        adaptador.clear()
                        val nuevaListaProgramas = EBaseDatos.tabla_SO_PR?.consultarProgramasPorIdSistemaOperativo(idSO)
                        if (nuevaListaProgramas != null) {
                            adaptador.addAll(nuevaListaProgramas) // Agregar la nueva lista al adaptador
                        }
                        adaptador.notifyDataSetChanged()
                    }

                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_programas)

        //recopilo la informacion pasada a traves del inted con parámetros
        var position = intent.getIntExtra("position", 0)
        val sistemaOperativo = datosSistemaOperativo?.get(position)
        idSO = sistemaOperativo?.id!!

        //ListView
        val tvNombreSO = findViewById<TextView>(R.id.tv_nombreSO)
        tvNombreSO.text = sistemaOperativo?.nombreSO
        val listViewProgramas = findViewById<ListView>(R.id.lv_programas)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            EBaseDatos.tabla_SO_PR?.consultarProgramasPorIdSistemaOperativo(idSO) ?: emptyList()
        )
        listViewProgramas.adapter = adaptador
        adaptador.notifyDataSetChanged()

        val botonCrearPrograma = findViewById<Button>(R.id.btn_crear_programa)
        botonCrearPrograma.setOnClickListener(){
            anadirPrograma(formCrearPrograma::class.java)
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
        val positionPrograma = info.position

        return when(item.itemId){
            R.id.mi_editar_programa -> {
                    editarPrograma(positionPrograma, idSO, formEditarProgram::class.java )
                true
            }
            R.id.mi_eliminar_programa -> {

                abrirDialogo(positionPrograma)
                true
            }
            else -> super.onContextItemSelected(item)
        }

    }
    fun abrirDialogo(positionPrograma: Int){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿Desea eliminar el programa?")
        builder.setPositiveButton(
            "Aceptar",
            DialogInterface.OnClickListener { dialog, which ->
                eliminarPrograma(positionPrograma)
            }
        )
        builder.setNegativeButton("Cancelar", null)

        val dialogo = builder.create()
        dialogo.show()
    }
    fun eliminarPrograma(positionPrograma: Int) {

        val listViewProgramas = findViewById<ListView>(R.id.lv_programas)
        val adaptador = listViewProgramas.adapter as ArrayAdapter<BPrograma>
        //Obtener la lista actual de elementos y eliminar el elemento en la posición dada
        val listaPrograma = adaptador.getItem(positionPrograma)
        val idPR = listaPrograma?.idPrograma
        if (idPR != null) {
            EBaseDatos.tabla_SO_PR?.eliminarProgramaFormulario(idPR, idSO)
        }
        adaptador.remove(listaPrograma)
        adaptador.notifyDataSetChanged()
    }
    fun anadirPrograma(clase: Class<*>){
        val intentExplicito = Intent(this, clase)
        callbackContenidoIntentExplicitoPrograma.launch(intentExplicito)
    }

    fun editarPrograma(posicionPR: Int, idSO: Int, clase: Class<*>) {
        val intentExplicitoEditarPrograma = Intent(this, clase)
        intentExplicitoEditarPrograma.putExtra("positionPr", posicionPR)
        intentExplicitoEditarPrograma.putExtra("idSO", idSO)
        callbackContenidoIntentExplicitoEditarPrograma.launch(intentExplicitoEditarPrograma)
    }

}