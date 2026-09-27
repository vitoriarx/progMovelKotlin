package com.example.conectarapiraca

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Remove a barra superior do Android
        supportActionBar?.hide()

        setContentView(R.layout.activity_main)

        val btnBuscar = findViewById<Button>(R.id.btnBuscar)

        btnBuscar.setOnClickListener {
            val intent = Intent(this, BuscaActivity::class.java)
            startActivity(intent)
        }
    }
}