package com.example.conectarapiraca

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SecaoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()
        setContentView(R.layout.activity_secao)

        // Botão voltar
        val btnVoltar = findViewById<TextView>(R.id.btnVoltarSecao)

        btnVoltar.setOnClickListener {
            finish()
        }

        // Identifica a seção selecionada
        val txtTitulo = findViewById<TextView>(R.id.txtTituloSecao)

        val nomeSecao = intent.getStringExtra("secao") ?: "Estabelecimentos"

        txtTitulo.text = nomeSecao

        // Identifica os cards
        val card1 = findViewById<LinearLayout>(R.id.btnExemplo1)
        val card2 = findViewById<LinearLayout>(R.id.btnExemplo2)

        // Identifica os nomes
        val txtNome1 = findViewById<TextView>(R.id.txtNomeEstabelecimento1)
        val txtNome2 = findViewById<TextView>(R.id.txtNomeEstabelecimento2)

        // Atualiza os nomes conforme a categoria
        txtNome1.text = "$nomeSecao 1"
        txtNome2.text = "$nomeSecao 2"

        // Os cards permanecem como elementos de visualização.
        // Não abrem outra tela ou modal.
    }
}
