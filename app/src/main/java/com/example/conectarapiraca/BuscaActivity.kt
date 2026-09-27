package com.example.conectarapiraca

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class BuscaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        setContentView(R.layout.activity_busca)

        val btnVoltar = findViewById<TextView>(R.id.btnVoltarBusca)

        btnVoltar.setOnClickListener {
            finish()
        }

        val btnRestaurantes = findViewById<Button>(R.id.btnRestaurantes)
        val btnAcademias = findViewById<Button>(R.id.btnAcademias)
        val btnLojas = findViewById<Button>(R.id.btnLojas)
        val btnBares = findViewById<Button>(R.id.btnBares)

        btnRestaurantes.setOnClickListener {
            abrirSecao("Restaurantes")
        }

        btnAcademias.setOnClickListener {
            abrirSecao("Academias")
        }

        btnLojas.setOnClickListener {
            abrirSecao("Lojas")
        }

        btnBares.setOnClickListener {
            abrirSecao("Bares")
        }
    }

    private fun abrirSecao(nomeSecao: String) {
        val intent = Intent(this, SecaoActivity::class.java)
        intent.putExtra("secao", nomeSecao)
        startActivity(intent)
    }
}