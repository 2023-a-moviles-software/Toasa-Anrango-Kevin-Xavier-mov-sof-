package com.example.examen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class formEditarProgram : AppCompatActivity() {
    val arregloCopiaSistemaOpeativo = BBaseDatosMemoria.arregloBSistemaOperativo
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_editar_program)

        //recopilo la informacion
        val positionPr = intent.getIntExtra("positionPr", -1)
        val positionSO = intent.getIntExtra("positionSO", -1)
        val sistemaOperativo = arregloCopiaSistemaOpeativo[positionSO]
        val programa = sistemaOperativo.programas

        //asigno a las casillas el nombre de cada array
        val nombrePr = findViewById<EditText>(R.id.tv_nombreMod)
        nombrePr.setText(programa?.get(positionPr)?.nombrePrograma)
        val versionPr = findViewById<EditText>(R.id.tv_versionMod)
        versionPr.setText(programa?.get(positionPr)?.version)
        val almacenamientoPr = findViewById<EditText>(R.id.tv_almacenamientoMod)
        almacenamientoPr.setText(programa?.get(positionPr)?.almacenamiento)

        //Boton
        val botonGuardarCambios = findViewById<Button>(R.id.btn_editar_programa)
        botonGuardarCambios.setOnClickListener(){
            val nuevoNombrePr = nombrePr.text.toString()
            val nuevaVersionPr = versionPr.text.toString()
            val nuevoAlamacenamientoPr = almacenamientoPr.text.toString()

            devolverRespuesta(nuevoNombrePr, nuevaVersionPr, nuevoAlamacenamientoPr, positionSO, positionPr)
        }
    }

    fun devolverRespuesta(nombrePr: String, versionPr: String, almacenamientoPr: String, posicionSO: Int, posicionPr: Int ){
        val intentDevolverParametros = Intent()
        intentDevolverParametros.putExtra("nuevoNombreSO", nombrePr)
        intentDevolverParametros.putExtra("nuevoVersionSO", versionPr)
        intentDevolverParametros.putExtra("nuevoAlmacenamiento", almacenamientoPr)
        intentDevolverParametros.putExtra("positionSO", posicionSO)
        intentDevolverParametros.putExtra("positionPR", posicionPr)
        setResult(
            RESULT_OK,
            intentDevolverParametros
        )

        finish()
    }
}