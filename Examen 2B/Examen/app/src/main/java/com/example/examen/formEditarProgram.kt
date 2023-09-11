package com.example.examen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.deber03.BPrograma
import com.example.deber03.BSistemaOperativo

class formEditarProgram : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_editar_program)
        val programa: BPrograma? = intent.getParcelableExtra("programas")
        val posicionPR = intent.getIntExtra("posicionPR", -1)
        //asigno a las casillas el nombre de cada array
        val nombrePr = findViewById<EditText>(R.id.tv_nombreMod)
        nombrePr.setText(programa?.nombrePrograma)
        val versionPr = findViewById<EditText>(R.id.tv_versionMod)
        versionPr.setText(programa?.version)
        val almacenamientoPr = findViewById<EditText>(R.id.tv_almacenamientoMod)
        almacenamientoPr.setText(programa?.almacenamiento)

        val botonGuardarCambios = findViewById<Button>(R.id.btn_editar_programa)
        botonGuardarCambios.setOnClickListener(){
            val nuevoNombrePr = nombrePr.text.toString()
            val nuevaVersionPr = versionPr.text.toString()
            val nuevoAlamacenamientoPr = almacenamientoPr.text.toString()
            devolverRespuesta(nuevoNombrePr, nuevaVersionPr, nuevoAlamacenamientoPr, posicionPR)
        }

    }
    fun devolverRespuesta(nombrePr: String, versionPr: String, almacenamientoPr: String, posicionPr:Int){
        val intentDevolverParametros = Intent()
        intentDevolverParametros.putExtra("nuevoNombreSO", nombrePr)
        intentDevolverParametros.putExtra("nuevoVersionSO", versionPr)
        intentDevolverParametros.putExtra("nuevoAlmacenamiento", almacenamientoPr)
        intentDevolverParametros.putExtra("posicion", posicionPr)
        setResult(
            RESULT_OK,
            intentDevolverParametros
        )
        finish()
    }
}