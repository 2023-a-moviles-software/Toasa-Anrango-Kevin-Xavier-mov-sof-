package com.example.examen

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog

class BListView : AppCompatActivity() {
    val arregloCopiaSistemaOpeativo = BBaseDatosMemoria.arregloBSistemaOperativo
    val callbackContenidoIntentExplicito =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
                result ->
            if(result.resultCode == Activity.RESULT_OK){
                if(result.data != null){
                    //Lógica de negocio

                    val data = result.data
                    val nuevoNombreSO = "${data?.getStringExtra("nuevoNombreSO")}"

                    val nuevaVersionSO = "${data?.getStringExtra("nuevoVersionSO")}"
                    val nuevaDistribucion = "${data?.getStringExtra("nuevaDistribucion")}"

                    val nuevoSistemaOperativo = BSistemaOperativo(
                        4, nuevoNombreSO, nuevaVersionSO, nuevaDistribucion, ArrayList<BPrograma>()
                    )

                    arregloCopiaSistemaOpeativo.add(nuevoSistemaOperativo)
                    BBaseDatosMemoria.arregloBSistemaOperativo = arregloCopiaSistemaOpeativo

                    val listView = findViewById<ListView>(R.id.lv_list_view)
                    val adaptador = listView.adapter as ArrayAdapter<BSistemaOperativo>
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

                    if (positionSO != null && positionSO != -1) {
                        val sistemaOperativo = arregloCopiaSistemaOpeativo[positionSO]
                        sistemaOperativo.nombreSO = nuevoNombreSO
                        sistemaOperativo.version = nuevaVersionSO
                        sistemaOperativo.distribucion = nuevaDistribucion
                        BBaseDatosMemoria.arregloBSistemaOperativo[positionSO] = sistemaOperativo
                        val listView = findViewById<ListView>(R.id.lv_list_view)
                        val adaptador = listView.adapter as ArrayAdapter<BSistemaOperativo>
                        adaptador.notifyDataSetChanged()
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blist_view)

        //Adaptador personalizado
        val listView = findViewById<ListView>(R.id.lv_list_view)
        val adaptador = object : ArrayAdapter<BSistemaOperativo>(
            this,
            android.R.layout.simple_list_item_1,
            BBaseDatosMemoria.arregloBSistemaOperativo
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)
                //Tomar solo el nombre del sistema Operativo
                val nombreSO = BBaseDatosMemoria.arregloBSistemaOperativo[position].nombreSO
                view.findViewById<TextView>(android.R.id.text1).text = nombreSO
                return view
            }
        }
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        val botonAniadirListView = findViewById<Button>(R.id.btn_anadir_list_view)
        botonAniadirListView.setOnClickListener(){
            crearSistemaOperativo(FormCrearSO::class.java)
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
                    dialog, which ->  eliminarElemento(position)//Hacer Algo
            }
        )
        builder.setNegativeButton("Cancelar", null)

        val dialogo = builder.create()
        dialogo.show()
    }

    fun eliminarElemento(position: Int) {
        arregloCopiaSistemaOpeativo.removeAt(position)
        BBaseDatosMemoria.arregloBSistemaOperativo = arregloCopiaSistemaOpeativo
        val listView = findViewById<ListView>(R.id.lv_list_view)
        val adaptador = listView.adapter as ArrayAdapter<BSistemaOperativo>
        adaptador.notifyDataSetChanged()
    }

    fun crearSistemaOperativo(clase: Class<*>){
        val intentExplicito = Intent(this, clase)
        //RECIBIMOS RESPUESTA
        callbackContenidoIntentExplicito.launch(intentExplicito)
    }




    private fun mostrarProgramas(position: Int) {
        val intent = Intent(this, listaProgramas::class.java)
        intent.putExtra("position", position)
        startActivity(intent)
    }

    private fun editarSistemaOperativo(position: Int, clase: Class<*>) {
        val intentExplicitoEditarSO = Intent(this, clase)
        intentExplicitoEditarSO.putExtra("positionSO", position)
        callbackContenidoIntentExplicitoEditar.launch(intentExplicitoEditarSO)
    }

}