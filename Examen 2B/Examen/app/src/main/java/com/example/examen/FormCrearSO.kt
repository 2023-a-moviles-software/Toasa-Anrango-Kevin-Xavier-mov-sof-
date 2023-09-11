package com.example.examen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class FormCrearSO : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_crear_so)

        val botonGuardar = findViewById<Button>(R.id.btn_guardarSO)
        botonGuardar.setOnClickListener(){
            val nombreSO = findViewById<EditText>(R.id.pt_nombreSO).text.toString()
            val version = findViewById<EditText>(R.id.pt_versionSO).text.toString()
            val distribucion = findViewById<EditText>(R.id.pt_distribusionSO).text.toString()
            if (nombreSO != null && version != null && distribucion != null) {
                devolverRespuesta(nombreSO, version, distribucion)
            }
        }
    }
    fun devolverRespuesta(nombreSO: String, version: String, distribucion: String ){
        val intentDevolverParametros = Intent()
        intentDevolverParametros.putExtra("nuevoNombreSO", nombreSO)
        intentDevolverParametros.putExtra("nuevoVersionSO", version)
        intentDevolverParametros.putExtra("nuevaDistribucion", distribucion)
        setResult(
            RESULT_OK,
            intentDevolverParametros
        )

        finish()
    }
}