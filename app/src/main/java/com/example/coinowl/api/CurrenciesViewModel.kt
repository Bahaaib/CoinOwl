package com.example.coinowl.api

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import org.json.JSONObject



class CurrenciesViewModel : ViewModel() {
    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default

    private val scope = CoroutineScope(coroutineContext)

    private val repository: CurrenciesRepository =
        CurrenciesRepository(Apifactory.currenciesApisApi)


    val response = MutableLiveData<String>()

    val currencies = MutableLiveData<MutableList<String>>()

    fun getRate(pair: String) {
        scope.launch {
            val requestResponse = repository.getRate(pair)
            response.postValue(requestResponse)
        }
    }

    fun getCurrencies() {
        scope.launch {
            val requestResponse = repository.getCurrencies()
            val reader = JSONObject(requestResponse)
            val curs = mutableListOf<String>()
            reader.getJSONObject("results").keys().forEach {
                curs.add(it)
            }
            currencies.postValue(curs)
        }
    }


    fun cancelAllRequests() = coroutineContext.cancel()
}