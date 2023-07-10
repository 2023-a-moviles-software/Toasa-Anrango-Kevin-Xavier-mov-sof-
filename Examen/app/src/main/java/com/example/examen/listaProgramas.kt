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

class listaProgramas : AppCompatActivity() {
    var arregloCopiaSistemaOpeativo = BBaseDatosMemoria.arregloBSistemaOperativo
    lateinit var sistemaOperativo: BSistemaOperativo
    lateinit var arregloprogramas: ArrayList<BPrograma>
    var position: Int = 0
    var positionPrograma: Int = 0

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

                    val nuevoPrograma = BPrograma(
                        4, nuevoNombrePrograma, nuevaAlmacenamientoPrograma, nuevaVersionPrograma
                    )
                    arregloprogramas.add(nuevoPrograma)
                    sistemaOperativo.programas = arregloprogramas
                    BBaseDatosMemoria.arregloBSistemaOperativo[position] = sistemaOperativo

                    val listViewProgramas = findViewById<ListView>(R.id.lv_programas)
                    val adaptador = listViewProgramas.adapter as ArrayAdapter<BPrograma>
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
                    val positionSO = data?.getIntExtra("positionSO", -1)
                    val positionPr = data?.getIntExtra("positionPR", -1)

                    if (positionSO != null && positionSO != -1 && positionPr != null && positionPr != -1) {
                        val sistemaOperativo = arregloCopiaSistemaOpeativo[positionSO]

                        if (sistemaOperativo.programas?.get(positionPr) != null ) {
                            sistemaOperativo.programas?.get(positionPr)!!.nombrePrograma = nuevoNombrePr
                            sistemaOperativo.programas?.get(positionPr)!!.version = nuevaVersionPr
                            sistemaOperativo.programas?.get(positionPr)!!.almacenamiento = nuevoAlmacenamiento
                        }
                        BBaseDatosMemoria.arregloBSistemaOperativo[positionSO] = sistemaOperativo
                        val listView = findViewById<ListView>(R.id.lv_programas)
                        val adaptador = listView.adapter as ArrayAdapter<BSistemaOperativo>
                        adaptador.notifyDataSetChanged()
                    }
                }
            }
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_programas)

        //recopilo la informacion pasada a traves del inted con parámetros
        position = intent.getIntExtra("position", 0)

        //copio el elemento del arreglo que selecciono y tambien los programas
        sistemaOperativo = arregloCopiaSistemaOpeativo[position]
        arregloprogramas = sistemaOperativo.programas ?: ArrayList()

        //identifico el text view creado en el layout, asigno un nombre al text view nombreSO y actualizo
        // el adapatador
        val tvNombreSO = findViewById<TextView>(R.id.tv_nombreSO)
        tvNombreSO.text = sistemaOperativo.nombreSO
        val listViewProgramas = findViewById<ListView>(R.id.lv_programas)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arregloprogramas
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
        positionPrograma = info.position

        return when(item.itemId){
            R.id.mi_editar_programa -> {
                editarPrograma(positionPrograma, position, formEditarProgram::class.java )
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
        arregloprogramas.removeAt(positionPrograma)
        sistemaOperativo.programas = arregloprogramas
        BBaseDatosMemoria.arregloBSistemaOperativo[position] = sistemaOperativo
        val listViewProgramas = findViewById<ListView>(R.id.lv_programas)
        val adaptador = listViewProgramas.adapter as ArrayAdapter<BPrograma>
        adaptador.notifyDataSetChanged()
    }

    fun anadirPrograma(clase: Class<*>){
        val intentExplicito = Intent(this, clase)
        callbackContenidoIntentExplicitoPrograma.launch(intentExplicito)
    }



    fun editarPrograma(position: Int, positionSO: Int, clase: Class<*>) {
        val intentExplicitoEditarSO = Intent(this, clase)
        intentExplicitoEditarSO.putExtra("positionPr", position)
        intentExplicitoEditarSO.putExtra("positionSO", positionSO)
        callbackContenidoIntentExplicitoEditarPrograma.launch(intentExplicitoEditarSO)
    }



}
