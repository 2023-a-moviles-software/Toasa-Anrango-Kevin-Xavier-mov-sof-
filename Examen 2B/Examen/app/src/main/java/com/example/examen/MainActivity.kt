package com.example.examen

import android.app.Activity
import android.app.DownloadManager
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
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.example.deber03.BPrograma
import com.example.deber03.BSistemaOperativo
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.Date


class MainActivity : AppCompatActivity() {
    val callbackContenidoIntentExplicito =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                if (result.data != null) {
                    val data = result.data
                    val nuevoNombreSO = data?.getStringExtra("nuevoNombreSO")
                    val nuevaVersionSO = data?.getStringExtra("nuevoVersionSO")
                    val nuevaDistribucion = data?.getStringExtra("nuevaDistribucion")

                    if (nuevoNombreSO != null && nuevaVersionSO != null && nuevaDistribucion != null) {
                        val db = Firebase.firestore
                        val sistemasOperativosRef = db.collection("sistemasOperativos")
                        // Crear un nuevo documento con los datos obtenidos
                        val identificador = nuevoNombreSO
                        val nuevoSistemaOperativo = hashMapOf(
                            "nombreSO" to nuevoNombreSO,
                            "version" to nuevaVersionSO,
                            "distribucion" to nuevaDistribucion,
                            "programasSO" to null  // Puedes establecer programasSO como null o proporcionar datos adicionales si es necesario
                        )

                        sistemasOperativosRef // (crear/actualizar)
                            .document(identificador.toString())
                            .set(nuevoSistemaOperativo)
                            .addOnSuccessListener {
                                val listView = findViewById<ListView>(R.id.lv_list_view)
                                val adaptador = ArrayAdapter(
                                    this,
                                    android.R.layout.simple_list_item_1,
                                    arreglo
                                )
                                listView.adapter = adaptador
                                consultarConOrderBy(adaptador)
                            }
                            .addOnFailureListener {  }
                    }
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

                    if (nuevoNombreSO != null && nuevaVersionSO != null && nuevaDistribucion != null) {
                            // Actualiza el objeto en Firebase Firestore
                            val db = Firebase.firestore
                            val sistemasOperativosRef = db.collection("sistemasOperativos")

                            // Obtiene el ID del documento a actualizar
                            val documentoId = nuevoNombreSO

                            if (documentoId != null) {
                                val nuevoSistemaOperativo = hashMapOf(
                                    "nombreSO" to nuevoNombreSO,
                                    "version" to nuevaVersionSO,
                                    "distribucion" to nuevaDistribucion
                                )
                                sistemasOperativosRef
                                    .document(documentoId)
                                    .update(nuevoSistemaOperativo as Map<String, Any>)
                                    .addOnSuccessListener {
                                        // Actualización exitosa en Firebase
                                        // Actualiza la vista o notifica el cambio de datos
                                        val listView = findViewById<ListView>(R.id.lv_list_view)
                                        val adaptador = ArrayAdapter(
                                            this,
                                            android.R.layout.simple_list_item_1,
                                            arreglo
                                        )
                                        listView.adapter = adaptador
                                        consultarConOrderBy(adaptador)
                                    }
                                    .addOnFailureListener {
                                        System.out.println("Error")// Manejar el error en caso de que la actualización falle
                                    }
                            }
                        }
                    }
                }
            }



    var query: Query? = null
    val arreglo: ArrayList<BSistemaOperativo> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Crear datos prueba

        crearDatosPrueba()

        // Configurando el list view
        val listView = findViewById<ListView>(R.id.lv_list_view)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arreglo
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        consultarConOrderBy(adaptador)

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

    private fun editarSistemaOperativo(position: Int, clase: Class<*>) {
        val intentExplicitoEditarSO = Intent(this, clase)
        intentExplicitoEditarSO.putExtra("elemento", arreglo[position])
       callbackContenidoIntentExplicitoEditar.launch(intentExplicitoEditarSO)
    }

    fun mostrarProgramas(position: Int) {
        val intent = Intent(this, listaProgramas::class.java)
        intent.putExtra("elemento", arreglo[position])
        startActivity(intent)
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
        val db = Firebase.firestore
        val sistemasOperativosRef = db.collection("sistemasOperativos")

        // Obtén el documento a eliminar a partir del elemento en la lista
        val sistemaOperativoAEliminar = arreglo[position]

        // Verifica que el objeto no sea nulo y que tenga un ID válido
        val documentoId = sistemaOperativoAEliminar.nombreSO
        if (documentoId != null) {
            // Elimina el documento de Firestore
            sistemasOperativosRef
                .document(documentoId)
                .delete()
                .addOnSuccessListener {
                    val listView = findViewById<ListView>(R.id.lv_list_view)
                    val adaptador = ArrayAdapter(
                        this,
                        android.R.layout.simple_list_item_1,
                        arreglo
                    )
                    listView.adapter = adaptador
                    // Eliminación exitosa, actualiza la vista
                    arreglo.removeAt(position)
                    adaptador.notifyDataSetChanged()
                }
                .addOnFailureListener {
                    // Manejar el error en caso de que la eliminación falle
                }
        }
    }





    fun crearSistemaOperativo(clase: Class<*>){
        val intentExplicito = Intent(this, clase)
        //RECIBIMOS RESPUESTA
        callbackContenidoIntentExplicito.launch(intentExplicito)
    }

        fun consultarConOrderBy(adaptador: ArrayAdapter<BSistemaOperativo>) {
            val db = Firebase.firestore
            val sistemasOperativosRef = db.collection("sistemasOperativos")

            limpiarArreglo()
            adaptador.notifyDataSetChanged()

            sistemasOperativosRef
                .orderBy("nombreSO", Query.Direction.ASCENDING)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    for (sistemaOperativo in querySnapshot.documents) {
                        anadirAArregloSistemaOperativo(sistemaOperativo)
                    }
                    adaptador.notifyDataSetChanged()
                }
                .addOnFailureListener { exception ->
                    // Manejar errores aquí
                }
        }


        fun anadirAArregloSistemaOperativo(sistemaOperativo: DocumentSnapshot) {
            val nombreSO = sistemaOperativo.getString("nombreSO")
            val version = sistemaOperativo.getString("version")
            val distribucion = sistemaOperativo.getString("distribucion")

            // Verificar si el campo "programasSO" existe y es una lista válida
            val programasSOData = sistemaOperativo.get("programasSO")
            val programas = mutableListOf<BPrograma>() // Crear una lista de programas vacía

            if (programasSOData is List<*>) {
                for (programaData in programasSOData) {
                    if (programaData is Map<*, *>) {
                        val nombrePrograma = programaData["nombrePrograma"] as? String ?: ""
                        val almacenamiento = programaData["almacenamiento"] as? String ?: ""
                        val versionPrograma = programaData["version"] as? String ?: ""
                        programas.add(BPrograma(nombrePrograma, almacenamiento, versionPrograma))
                    }
                }
            }

            val nuevoSistemaOperativo = BSistemaOperativo(nombreSO, version, distribucion, programas)
            arreglo.add(nuevoSistemaOperativo)
        }


    fun limpiarArreglo() {
        arreglo.clear()
    }


    fun crearDatosPrueba() {
        val db = Firebase.firestore

        // Crear programas para los sistemas operativos
        val programasSO1 = listOf(
            hashMapOf(
                "nombrePrograma" to "App1",
                "almacenamiento" to "512 MB",
                "version" to "1.0"
            )
        )

        val programasSO2 = listOf(
            hashMapOf(
                "nombrePrograma" to "App2",
                "almacenamiento" to "256 MB",
                "version" to "2.0"
            )
        )

        val programasSO3 = listOf(
            hashMapOf(
                "nombrePrograma" to "App3",
                "almacenamiento" to "1 GB",
                "version" to "3.0"
            )
        )

        val programasSO4 = listOf(
            hashMapOf(
                "nombrePrograma" to "App4",
                "almacenamiento" to "512 MB",
                "version" to "1.0"
            )
        )

        // Crear sistemas operativos con listas de programas
        val sistemaOperativo1 = hashMapOf(
            "nombreSO" to "Android",
            "version" to "12.0",
            "distribucion" to "Android",
            "programasSO" to programasSO1
        )

        val sistemaOperativo2 = hashMapOf(
            "nombreSO" to "iOS",
            "version" to "15.0",
            "distribucion" to "iOS",
            "programasSO" to programasSO2
        )

        val sistemaOperativo3 = hashMapOf(
            "nombreSO" to "Windows",
            "version" to "11",
            "distribucion" to "Windows",
            "programasSO" to programasSO3
        )

        val sistemaOperativo4 = hashMapOf(
            "nombreSO" to "macOS",
            "version" to "12.0",
            "distribucion" to "macOS",
            "programasSO" to programasSO4
        )

        // Guardar los datos en Firestore
        db.collection("sistemasOperativos").document("Android").set(sistemaOperativo1)
        db.collection("sistemasOperativos").document("iOS").set(sistemaOperativo2)
        db.collection("sistemasOperativos").document("Windows").set(sistemaOperativo3)
        db.collection("sistemasOperativos").document("macOS").set(sistemaOperativo4)
    }

}