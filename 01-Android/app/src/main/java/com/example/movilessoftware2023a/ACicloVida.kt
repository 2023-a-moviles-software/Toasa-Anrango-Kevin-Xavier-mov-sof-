package com.example.movilessoftware2023a

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ACicloVida : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aciclo_vida)
    }

    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
        //this.startActivity()
    }
}
