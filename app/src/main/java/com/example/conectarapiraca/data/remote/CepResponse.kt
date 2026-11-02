package com.example.conectarapiraca.data.remote

data class CepResponse(
    val cep: String?,
    val state: String?,
    val city: String?,
    val neighborhood: String?,
    val street: String?
)