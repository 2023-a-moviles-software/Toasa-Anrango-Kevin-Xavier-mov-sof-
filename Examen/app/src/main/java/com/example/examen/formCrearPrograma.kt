package com.example.examen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class formCrearPrograma : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_crear_programa)
        val botonGuardar = findViewById<Button>(R.id.btn_crearPrograma)
        botonGuardar.setOnClickListener(){
            val nombrePr = findViewById<EditText>(R.id.tv_nombrePrograma).text.toString()
            val versionPr = findViewById<EditText>(R.id.tv_versionPrograma).text.toString()
            val almacenamientoPr = findViewById<EditText>(R.id.tv_almacenamientoPro).text.toString()
            devolverRespuesta(nombrePr, versionPr, almacenamientoPr)
        }
    }
    fun devolverRespuesta(nombreSO: String, version: String, almacenamiento: String ){
        val intentDevolverParametros = Intent()
        intentDevolverParametros.putExtra("nuevoNombrePr", nombreSO)
        intentDevolverParametros.putExtra("nuevoVersionPr", version)
        intentDevolverParametros.putExtra("nuevoAlmacenamientoPr", almacenamiento)

        setResult(
            RESULT_OK,
            intentDevolverParametros
        )

        finish()
    }
}