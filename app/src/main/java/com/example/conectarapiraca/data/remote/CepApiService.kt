package com.example.conectarapiraca.data.remote

import retrofit2.http.GET
import retrofit2.http.Path

interface CepApiService {

    @GET("api/cep/v2/{cep}")
    suspend fun buscarCep(
        @Path("cep") cep: String
    ): CepResponse
}