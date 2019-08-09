package com.example.coinowl.api

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrenciesApi {
    @GET("convert")
    fun getRate(
        @Query("q") pair: String,
        @Query("compact") compat: String="ultra"
    ): Deferred<Response<String>>

    @GET("currencies")
    fun getCurrencies(@Query("compact") compat: String="ultra"): Deferred<Response<String>>
}