package com.example.conectarapiraca.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface EstabelecimentoDao {

    @Insert
    suspend fun inserir(estabelecimento: Estabelecimento)

    @Query("SELECT * FROM estabelecimentos WHERE categoria = :categoria")
    suspend fun buscarPorCategoria(categoria: String): List<Estabelecimento>

    @Query("SELECT * FROM estabelecimentos WHERE cep = :cep AND categoria = :categoria")
    suspend fun buscarPorCepECategoria(
        cep: String,
        categoria: String
    ): List<Estabelecimento>
}