package com.example.coinowl.api

class CurrenciesRepository(private val api : CurrenciesApi) : BaseRepository()  {
    suspend fun getRate(pair: String) : String?{

        val response = safeApiCall(
            call = {api.getRate(pair).await()},
            errorMessage = "Error Fetching Popular Movies"
        )

        return response

    }

    suspend fun getCurrencies() : String?{

        val response = safeApiCall(
            call = {api.getCurrencies().await()},
            errorMessage = "Error Fetching Popular Movies"
        )

        return response

    }


}