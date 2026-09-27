package com.example.conectarapiraca

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SecaoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        setContentView(R.layout.activity_secao)

        val btnVoltar = findViewById<TextView>(R.id.btnVoltarSecao)

        btnVoltar.setOnClickListener {
            finish()
        }

        val txtTitulo = findViewById<TextView>(R.id.txtTituloSecao)

        val nomeSecao = intent.getStringExtra("secao")

        txtTitulo.text = nomeSecao
    }
}