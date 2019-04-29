package com.example.monedas.api

import com.example.monedas.models.RespuestaMoneda
import retrofit2.Call
import retrofit2.http.GET

interface apiService {
    @GET("monedas.json")
    fun obtenerListaMonedas(): Call<RespuestaMoneda>
}