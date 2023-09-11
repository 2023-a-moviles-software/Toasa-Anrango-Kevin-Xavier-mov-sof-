package com.example.examen

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
import com.example.deber03.BPrograma
import com.example.deber03.BSistemaOperativo
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class listaProgramas : AppCompatActivity() {
    val callbackContenidoIntentExplicitoPrograma = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                if (result.data != null) {
                    val data = result.data
                    val nuevoNombrePrograma = data?.getStringExtra("nuevoNombrePr")
                    val nuevaVersionPrograma = data?.getStringExtra("nuevoVersionPr")
                    val nuevaAlmacenamientoPrograma = data?.getStringExtra("nuevoAlmacenamientoPr")

                    // Verificar que los datos no sean nulos antes de agregar el programa
                    if (nuevoNombrePrograma != null && nuevaVersionPrograma != null && nuevaAlmacenamientoPrograma != null) {
                        // Crear un nuevo BPrograma con los datos
                        val nuevoPrograma = BPrograma(nuevoNombrePrograma, nuevaAlmacenamientoPrograma, nuevaVersionPrograma)

                        // Agregar el nuevo programa a la lista de programas del sistema operativo
                        programas?.add(nuevoPrograma)

                        // Actualizar la lista de programas en Firestore
                        val db = Firebase.firestore
                        val sistemasOperativosRef = db.collection("sistemasOperativos")

                        // Obtén el ID del documento del sistema operativo actual
                        val documentoId = sistemaOperativo?.nombreSO

                        if (documentoId != null) {
                            val datosActualizados = hashMapOf(
                                "programasSO" to sistemaOperativo?.programasSO
                            )

                            sistemasOperativosRef
                                .document(documentoId)
                                .update(datosActualizados as Map<String, Any>)
                                .addOnSuccessListener {
                                    // Notificar al adaptador que se ha actualizado la lista de programas


                                    val listaProgramas = programas?.map { it.toString() } ?: emptyList()
                                    val listViewProgramas = findViewById<ListView>(R.id.lv_programas)
                                    val adaptador = ArrayAdapter(
                                        this,
                                        android.R.layout.simple_list_item_1,
                                        listaProgramas
                                    )
                                    //ListView
                                    listViewProgramas.adapter = adaptador
                                    adaptador.notifyDataSetChanged()



                                }
                                .addOnFailureListener {
                                    // Manejar el error en caso de que la actualización en Firestore falle
                                }
                        }
                    }
                }
            }
        }

    val callbackContenidoIntentExplicitoEditarPrograma =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                if (result.data != null) {
                    val data = result.data
                    val nuevoNombrePr = data?.getStringExtra("nuevoNombrePr")
                    val nuevaVersionPr = data?.getStringExtra("nuevoVersionPr")
                    val nuevoAlmacenamiento = data?.getStringExtra("nuevoAlmacenamiento")
                    val posicionPrograma = data?.getIntExtra("posicion", -1)

                    // Verificar que los datos no sean nulos y que la posición sea válida
                    if (nuevoNombrePr != null && nuevaVersionPr != null && nuevoAlmacenamiento != null && posicionPrograma != -1) {
                        val programaEditado = BPrograma(nuevoNombrePr, nuevoAlmacenamiento, nuevaVersionPr)
                        if (posicionPrograma != null) {
                            eliminarPrograma(posicionPrograma)
                        }
                        // creaaaaaaaaaaaaaa
                        // Agregar el nuevo programa a la lista de programas del sistema operativo
                        programas?.add(programaEditado)

                        // Actualizar la lista de programas en Firestore
                        val db = Firebase.firestore
                        val sistemasOperativosRef = db.collection("sistemasOperativos")

                        // Obtén el ID del documento del sistema operativo actual
                        val documentoId = sistemaOperativo?.nombreSO

                        if (documentoId != null) {
                            val datosActualizados = hashMapOf(
                                "programasSO" to sistemaOperativo?.programasSO
                            )

                            sistemasOperativosRef
                                .document(documentoId)
                                .update(datosActualizados as Map<String, Any>)
                                .addOnSuccessListener {
                                    // Notificar al adaptador que se ha actualizado la lista de programas


                                    val listaProgramas = programas?.map { it.toString() } ?: emptyList()
                                    val listViewProgramas = findViewById<ListView>(R.id.lv_programas)
                                    val adaptador = ArrayAdapter(
                                        this,
                                        android.R.layout.simple_list_item_1,
                                        listaProgramas
                                    )
                                    //ListView
                                    listViewProgramas.adapter = adaptador
                                    adaptador.notifyDataSetChanged()



                                }
                                .addOnFailureListener {
                                    // Manejar el error en caso de que la actualización en Firestore falle
                                }
                        }

                    }
                }
            }
        }




    var sistemaOperativo: BSistemaOperativo? = null
    var programas: ArrayList<BPrograma>? = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_programas)


        sistemaOperativo = intent.getParcelableExtra("elemento")
        programas = sistemaOperativo?.programasSO as ArrayList<BPrograma>?
        val tvNombreSO = findViewById<TextView>(R.id.tv_nombreSO)
        tvNombreSO.text = sistemaOperativo?.nombreSO
        val listaProgramas = programas?.map { it.toString() } ?: emptyList()
        val listViewProgramas = findViewById<ListView>(R.id.lv_programas)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            listaProgramas
        )
        //ListView
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
                editarPrograma(positionPrograma, formEditarProgram::class.java )
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

    fun eliminarPrograma(position: Int) {
        val programaAEliminar = programas?.get(position)

        if (programaAEliminar != null) {
            val db = Firebase.firestore
            val sistemasOperativosRef = db.collection("sistemasOperativos")
            val documentoId = sistemaOperativo?.nombreSO

            if (documentoId != null) {
                sistemasOperativosRef
                    .document(documentoId)
                    .update("programasSO", FieldValue.arrayRemove(programaAEliminar))
                    .addOnSuccessListener {
                        // Eliminación exitosa, actualiza la lista local
                        programas?.removeAt(position)

                        val adaptador = ArrayAdapter(
                            this,
                            android.R.layout.simple_list_item_1,
                            programas?.map { it.toString() } ?: emptyList()
                        )

                        val listViewProgramas = findViewById<ListView>(R.id.lv_programas)
                        listViewProgramas.adapter = adaptador
                        adaptador.notifyDataSetChanged()
                    }
                    .addOnFailureListener {
                        // Manejar el error en caso de que la eliminación falle
                    }
            }
        }
    }


    fun anadirPrograma(clase: Class<*>){
        val intentExplicito = Intent(this, clase)
        callbackContenidoIntentExplicitoPrograma.launch(intentExplicito)
    }

    fun editarPrograma(posicionPR: Int, clase: Class<*>) {
        val intentExplicitoEditarPrograma = Intent(this, clase)
        intentExplicitoEditarPrograma.putExtra("programas", programas?.get(posicionPR))
        intentExplicitoEditarPrograma.putExtra("posicion", posicionPR)
        callbackContenidoIntentExplicitoEditarPrograma.launch(intentExplicitoEditarPrograma)
    }
}