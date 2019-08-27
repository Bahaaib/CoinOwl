package com.example.coinowl.api

import android.util.Log
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CurrenciesRepository(private val api : CurrenciesApi) : BaseRepository()  {
    suspend fun getRate(pair: String) : String?{
        val now: LocalDateTime = LocalDateTime.now()
        val daysAgo = now.minusDays(8)
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return safeApiCall(
            call = { api.getRate(pair, daysAgo.format(formatter), now.format(formatter)).await()},
            errorMessage = "Error Fetching Rate"
        )

    }

    suspend fun getCurrencies() : String?{

        return safeApiCall(
            call = { api.getCurrencies().await()},
            errorMessage = "Error Fetching Popular Movies"
        )

    }


}