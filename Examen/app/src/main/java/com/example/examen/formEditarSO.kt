package com.example.examen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class formEditarSO : AppCompatActivity() {
    val arregloCopiaSistemaOpeativo = BBaseDatosMemoria.arregloBSistemaOperativo
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_editar_so)

        //recopilo la informacion
        val positionSO = intent.getIntExtra("positionSO", -1)
        val sistemaOperativo = arregloCopiaSistemaOpeativo[positionSO]

        //asigno a las casillas el nombre de cada array
        val nombreSO = findViewById<EditText>(R.id.pt_cambiar_nombreSO)
        nombreSO.setText(sistemaOperativo.nombreSO)
        val versionSO = findViewById<EditText>(R.id.pt_cambiar_versionSO)
        versionSO.setText(sistemaOperativo.version)
        val distribucionSO = findViewById<EditText>(R.id.pt_cambiar_distribusionSO)
        distribucionSO.setText(sistemaOperativo.distribucion)

        //Boton
        val botonGuardarCambios = findViewById<Button>(R.id.btn_guardarCambiosSO)
        botonGuardarCambios.setOnClickListener(){
            val nuevoNombreSO = nombreSO.text.toString()
            val nuevaVersionSO = versionSO.text.toString()
            val nuevaDistribucion = distribucionSO.text.toString()

            devolverRespuesta(nuevoNombreSO, nuevaVersionSO, nuevaDistribucion, positionSO)
        }
    }

    fun devolverRespuesta(nombreSO: String, version: String, distribucion: String, posicion: Int ){
        val intentDevolverParametros = Intent()
        intentDevolverParametros.putExtra("nuevoNombreSO", nombreSO)
        intentDevolverParametros.putExtra("nuevoVersionSO", version)
        intentDevolverParametros.putExtra("nuevaDistribucion", distribucion)
        intentDevolverParametros.putExtra("positionSO", posicion)

        setResult(
            RESULT_OK,
            intentDevolverParametros
        )

        finish()
    }
}