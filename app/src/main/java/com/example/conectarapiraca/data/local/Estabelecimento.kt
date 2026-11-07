package com.example.conectarapiraca.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "estabelecimentos")
data class Estabelecimento(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val nome: String,
    val categoria: String,
    val endereco: String,
    val cep: String,
    val telefone: String,
    val avaliacao: Double
)