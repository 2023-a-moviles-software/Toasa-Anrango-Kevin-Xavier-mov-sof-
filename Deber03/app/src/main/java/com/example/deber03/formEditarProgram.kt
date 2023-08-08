package com.example.deber03

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class formEditarProgram : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_editar_program)
        //recopilo la informacion
        val posicionPR = intent.getIntExtra("positionPr", -1)
        val idSO = intent.getIntExtra("idSO", -1)
        var datosPr = EBaseDatos.tabla_SO_PR?.consultarProgramasPorIdSistemaOperativo(idSO)
        val programa = datosPr?.get(posicionPR)
        val idPR = programa?.idPrograma

        //asigno a las casillas el nombre de cada array
        val nombrePr = findViewById<EditText>(R.id.tv_nombreMod)
        nombrePr.setText(programa?.nombrePrograma)
        val versionPr = findViewById<EditText>(R.id.tv_versionMod)
        versionPr.setText(programa?.version)
        val almacenamientoPr = findViewById<EditText>(R.id.tv_almacenamientoMod)
        almacenamientoPr.setText(programa?.almacenamiento)

        //Boton
        val botonGuardarCambios = findViewById<Button>(R.id.btn_editar_programa)
        botonGuardarCambios.setOnClickListener(){
            val nuevoNombrePr = nombrePr.text.toString()
            val nuevaVersionPr = versionPr.text.toString()
            val nuevoAlamacenamientoPr = almacenamientoPr.text.toString()
            if (idPR != null) {
                devolverRespuesta(nuevoNombrePr, nuevaVersionPr, nuevoAlamacenamientoPr, idSO, idPR)
            }
        }
    }
    fun devolverRespuesta(nombrePr: String, versionPr: String, almacenamientoPr: String, idSO: Int, idPR: Int ){
        val intentDevolverParametros = Intent()
        intentDevolverParametros.putExtra("nuevoNombreSO", nombrePr)
        intentDevolverParametros.putExtra("nuevoVersionSO", versionPr)
        intentDevolverParametros.putExtra("nuevoAlmacenamiento", almacenamientoPr)
        intentDevolverParametros.putExtra("idSO", idSO)
        intentDevolverParametros.putExtra("idPR", idPR)
        setResult(
            RESULT_OK,
            intentDevolverParametros
        )

        finish()
    }
}