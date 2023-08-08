package com.example.deber03

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    val callbackContenidoIntentExplicito =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                if (result.data != null) {
                    // Lógica de negocio
                    val data = result.data
                    val nuevoNombreSO = "${data?.getStringExtra("nuevoNombreSO")}"
                    val nuevaVersionSO = "${data?.getStringExtra("nuevoVersionSO")}"
                    val nuevaDistribucion = "${data?.getStringExtra("nuevaDistribucion")}"

                    // Crear el sistema operativo
                    EBaseDatos.tabla_SO_PR?.crearSistemaOperativo(nuevoNombreSO, nuevaVersionSO, nuevaDistribucion)

                    // Actualizar la lista de sistemas operativos en el adaptador
                    val listView = findViewById<ListView>(R.id.lv_list_view)
                    val adaptador = listView.adapter as ArrayAdapter<BSistemaOperativo>
                    adaptador.clear() // Limpiar la lista actual en el adaptador
                    val nuevaListaSistemasOperativos = EBaseDatos.tabla_SO_PR?.consultarTablaSistemaOperativo()
                    if (nuevaListaSistemasOperativos != null) {
                        adaptador.addAll(nuevaListaSistemasOperativos) // Agregar la nueva lista al adaptador
                    }

                    // Notificar al adaptador que los datos han cambiado
                    adaptador.notifyDataSetChanged()
                }
            }
        }
    val callbackContenidoIntentExplicitoEditar =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                if (result.data != null) {
                    val data = result.data
                    val nuevoNombreSO = data?.getStringExtra("nuevoNombreSO")
                    val nuevaVersionSO = data?.getStringExtra("nuevoVersionSO")
                    val nuevaDistribucion = data?.getStringExtra("nuevaDistribucion")
                    val positionSO = data?.getIntExtra("positionSO", -1)

                    // obtener informacion del elemento
                    val listView = findViewById<ListView>(R.id.lv_list_view)
                    val adaptador = listView.adapter as ArrayAdapter<BSistemaOperativo>
                    val listaSistemasOperativos = adaptador.getItem(positionSO!!)
                    val idSO = listaSistemasOperativos?.id
                    if (positionSO != null && positionSO != -1) {
                        //Actualizar Base de datos
                        if (idSO != null && nuevoNombreSO != null && nuevaVersionSO != null && nuevaDistribucion != null) {
                            EBaseDatos.tabla_SO_PR?.actualizarSOFormulario(idSO, nuevoNombreSO, nuevaVersionSO, nuevaDistribucion)
                        }
                        // Actualizar la lista de sistemas operativos en el adaptador
                        val listView = findViewById<ListView>(R.id.lv_list_view)
                        val adaptador = listView.adapter as ArrayAdapter<BSistemaOperativo>
                        adaptador.clear() // Limpiar la lista actual en el adaptador
                        val nuevaListaSistemasOperativos = EBaseDatos.tabla_SO_PR?.consultarTablaSistemaOperativo()
                        if (nuevaListaSistemasOperativos != null) {
                            adaptador.addAll(nuevaListaSistemasOperativos) // Agregar la nueva lista al adaptador
                        }
                        // Notificar al adaptador que los datos han cambiado
                        adaptador.notifyDataSetChanged()
                    }
                }
            }
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        EBaseDatos.tabla_SO_PR = ESqliteHelper(this)

        //Adaptador personalizado
        val listView = findViewById<ListView>(R.id.lv_list_view)
        var datosSistemaOperativo = EBaseDatos.tabla_SO_PR?.consultarTablaSistemaOperativo()
        if (datosSistemaOperativo == null || datosSistemaOperativo.isEmpty()) {
            for (sistemaOperativo in listaSistemaOperativo.arregloSistemaOperativo) {
                val nombreSO = sistemaOperativo.nombreSO
                val version = sistemaOperativo.version
                val distribucion = sistemaOperativo.distribucion

                if (nombreSO != null && version != null && distribucion != null) {
                    EBaseDatos.tabla_SO_PR?.crearSistemaOperativo(nombreSO, version, distribucion)
                }
            }
            for (programa in listaPrograma.arregloProgramas) {
                val idSO = programa.idSO
                val nombrePrograma = programa.nombrePrograma
                val almacenamiento = programa.almacenamiento
                val version = programa.version

                if (nombrePrograma != null && almacenamiento != null && version != null) {
                    EBaseDatos.tabla_SO_PR?.crearPrograma(idSO, nombrePrograma, almacenamiento, version)
                }
            }
        }


        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            EBaseDatos.tabla_SO_PR?.consultarTablaSistemaOperativo() ?: emptyList()
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()

        val botonAniadirListView = findViewById<Button>(R.id.btn_anadir_list_view)
        botonAniadirListView.setOnClickListener(){
            crearSistemaOperativo(FormCrearSO::class.java)
        }
        registerForContextMenu(listView)
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?){
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val positionItemSeleccionado = info.position
        return when(item.itemId){
            R.id.mi_editar ->{
                editarSistemaOperativo(positionItemSeleccionado, formEditarSO::class.java)
                //Hacer algo con idSeleccionado
                return true
            }
            R.id.mi_eliminar ->{
                abrirDialogo(positionItemSeleccionado)
                //Hacer algo con idSeleccionado
                return true
            }
            R.id.mi_ver_programas -> {
                mostrarProgramas(positionItemSeleccionado)
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
                    dialog, which ->  eliminarElemento(position)
            }
        )
        builder.setNegativeButton("Cancelar", null)

        val dialogo = builder.create()
        dialogo.show()
    }

    fun eliminarElemento(position: Int) {

        val listView = findViewById<ListView>(R.id.lv_list_view)
        val adaptador = listView.adapter as ArrayAdapter<BSistemaOperativo>

        // Obtener la lista actual de elementos y eliminar el elemento en la posición dada
        val listaSistemasOperativos = adaptador.getItem(position)
        val idSO = listaSistemasOperativos?.id
        if (idSO != null) {
            EBaseDatos.tabla_SO_PR?.eliminarSOFormulario(idSO)
        }
        adaptador.remove(listaSistemasOperativos)

        // Notificar al adaptador que los datos han cambiado
        adaptador.notifyDataSetChanged()
    }

    fun crearSistemaOperativo(clase: Class<*>){
        val intentExplicito = Intent(this, clase)
        //RECIBIMOS RESPUESTA
        callbackContenidoIntentExplicito.launch(intentExplicito)
    }

    private fun editarSistemaOperativo(position: Int, clase: Class<*>) {
        val intentExplicitoEditarSO = Intent(this, clase)
        intentExplicitoEditarSO.putExtra("positionSO", position)
        callbackContenidoIntentExplicitoEditar.launch(intentExplicitoEditarSO)
    }
    fun mostrarProgramas(position: Int) {
        val intent = Intent(this, listaProgramas::class.java)
        intent.putExtra("position", position)
        startActivity(intent)
    }
}