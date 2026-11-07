
package com.example.conectarapiraca

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.conectarapiraca.data.local.AppDatabase
import kotlinx.coroutines.launch
import android.util.Log
import android.widget.Toast

class SecaoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_secao)

        val btnVoltar = findViewById<TextView>(R.id.btnVoltarSecao)
        btnVoltar.setOnClickListener { finish() }

        val nomeSecao = intent.getStringExtra("secao") ?: "Estabelecimentos"
        val cep = intent.getStringExtra("cep")

        findViewById<TextView>(R.id.txtTituloSecao).text = nomeSecao

        val card1 = findViewById<LinearLayout>(R.id.btnExemplo1)
        val card2 = findViewById<LinearLayout>(R.id.btnExemplo2)

        val txtNome1 = findViewById<TextView>(R.id.txtNomeEstabelecimento1)
        val txtNome2 = findViewById<TextView>(R.id.txtNomeEstabelecimento2)

        val txtAvaliacao1 = findViewById<TextView>(R.id.txtAvaliacao1)
        val txtAvaliacao2 = findViewById<TextView>(R.id.txtAvaliacao2)

        val txtEndereco1 = findViewById<TextView>(R.id.txtEndereco1)
        val txtEndereco2 = findViewById<TextView>(R.id.txtEndereco2)

        val txtTelefone1 = findViewById<TextView>(R.id.txtTelefone1)
        val txtTelefone2 = findViewById<TextView>(R.id.txtTelefone2)

        card1.visibility = View.GONE
        card2.visibility = View.GONE

        lifecycleScope.launch {
            try {
                val dao = AppDatabase.getDatabase(applicationContext)
                    .estabelecimentoDao()

                // Mantém só os dígitos, para "57350-000" virar "57350000"
                val cepLimpo = cep?.filter { it.isDigit() }

                val estabelecimentos = if (!cepLimpo.isNullOrBlank()) {
                    dao.buscarPorCepECategoria(cepLimpo, nomeSecao)
                } else {
                    dao.buscarPorCategoria(nomeSecao)
                }

                Log.d("SecaoActivity", "secao=$nomeSecao cep=$cepLimpo encontrados=${estabelecimentos.size}")

                if (estabelecimentos.isEmpty()) {
                    Toast.makeText(
                        this@SecaoActivity,
                        "Nenhum estabelecimento encontrado para esta categoria.",
                        Toast.LENGTH_LONG
                    ).show()
                    return@launch
                }

                val primeiro = estabelecimentos[0]
                txtNome1.text = primeiro.nome
                txtAvaliacao1.text = "Avaliação: ${primeiro.avaliacao}"
                txtEndereco1.text = primeiro.endereco
                txtTelefone1.text = primeiro.telefone
                card1.visibility = View.VISIBLE

                if (estabelecimentos.size > 1) {
                    val segundo = estabelecimentos[1]
                    txtNome2.text = segundo.nome
                    txtAvaliacao2.text = "Avaliação: ${segundo.avaliacao}"
                    txtEndereco2.text = segundo.endereco
                    txtTelefone2.text = segundo.telefone
                    card2.visibility = View.VISIBLE
                }

            } catch (e: Exception) {
                Log.e("SecaoActivity", "Erro ao consultar o Room", e)
                Toast.makeText(
                    this@SecaoActivity,
                    "Erro ao carregar os dados locais.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}

