package com.example.conectarapiraca
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.conectarapiraca.R
import com.example.conectarapiraca.data.remote.RetrofitClient
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class ResultadosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()
        setContentView(R.layout.activity_resultados)

        // Componentes da interface
        val btnVoltar = findViewById<TextView>(R.id.btnVoltarResultados)
        val edtCep = findViewById<EditText>(R.id.edtBusca)
        val btnConsultar = findViewById<Button>(R.id.btnConsultarCep)
        val txtResultado = findViewById<TextView>(R.id.txtResultadoCep)
        val layoutCategorias = findViewById<LinearLayout>(R.id.layoutCategorias)

        // Botão voltar
        btnVoltar.setOnClickListener {
            finish()
        }

        // Botões de categorias
        val btnRestaurantes = findViewById<Button>(
            R.id.btnCategoriaRestaurantes
        )

        val btnAcademias = findViewById<Button>(
            R.id.btnCategoriaAcademias
        )

        val btnLojas = findViewById<Button>(
            R.id.btnCategoriaLojas
        )

        val btnBares = findViewById<Button>(
            R.id.btnCategoriaBares
        )

        // Categorias começam ocultas
        layoutCategorias.visibility = View.GONE
        txtResultado.visibility = View.GONE

        // Consulta do CEP
        btnConsultar.setOnClickListener {

            val cep = edtCep.text.toString().trim()

            // Validação
            if (cep.length != 8 || !cep.all { it.isDigit() }) {

                Toast.makeText(
                    this,
                    "Digite um CEP válido com 8 números",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            // Coroutine
            lifecycleScope.launch {

                btnConsultar.isEnabled = false // Desliga o botão pra o usuário não clicar mais de um vez

                // Exibe mensagem de carregamento
                txtResultado.visibility = View.VISIBLE
                txtResultado.text = "Consultando endereço..."

                layoutCategorias.visibility = View.GONE // Esconde as categorias de uma consulta anterior

                try {

                    // Consumo da BrasilAPI com Retrofit e Coroutines
                    val resposta = RetrofitClient.api.buscarCep(cep)

                    // Confirma que a resposta possui dados de endereço
                    if (resposta.cep.isNullOrBlank() ||
                        resposta.city.isNullOrBlank() ||
                        resposta.state.isNullOrBlank()
                    ) {
                        throw IllegalStateException("Resposta inválida da API")
                    }

                    // Exibe os dados retornados
                    txtResultado.text = """
                        Endereço encontrado!

                        CEP: ${resposta.cep}
                        Estado: ${resposta.state}
                        Cidade: ${resposta.city}
                        Bairro: ${resposta.neighborhood?.ifBlank { "Não informado" } ?: "Não informado"}
                        Rua: ${resposta.street?.ifBlank { "Não informado" } ?: "Não informado"}
                    """.trimIndent()

                    // Exibe categorias após consulta bem-sucedida
                    layoutCategorias.visibility = View.VISIBLE

                    // Configura navegação das categorias
                    btnRestaurantes.setOnClickListener {
                        abrirSecao("Restaurantes", cep)
                    }

                    btnAcademias.setOnClickListener {
                        abrirSecao("Academias", cep)
                    }

                    btnLojas.setOnClickListener {
                        abrirSecao("Lojas", cep)
                    }

                    btnBares.setOnClickListener {
                        abrirSecao("Bares", cep)
                    }

                } catch (e: HttpException) {

                    txtResultado.text =
                        if (e.code() == 404) {
                            "CEP não encontrado. Verifique o número informado."
                        } else {
                            "Erro na consulta. Código: ${e.code()}"
                        }

                    Log.e("CONSULTA_CEP", "Erro HTTP", e) // Registra o logcat pra poder depurar

                } catch (e: IOException) {

                    txtResultado.text =
                        "Erro de conexão. Verifique sua internet."

                    Log.e("CONSULTA_CEP", "Erro de conexão", e)

                } catch (e: Exception) {

                    txtResultado.text =
                        "Não foi possível consultar o CEP: ${e.message}"

                    Log.e("CONSULTA_CEP", "Erro inesperado", e)

                } finally {

                    btnConsultar.isEnabled = true
                }
            }
        }
    }

    // Abre a seção selecionada (secaoActivity) e envia o CEP consultado
    private fun abrirSecao(nomeSecao: String, cep: String) {

        val intent = Intent(this, SecaoActivity::class.java)

        intent.putExtra("secao", nomeSecao)
        intent.putExtra("cep", cep)

        startActivity(intent)
    }
}