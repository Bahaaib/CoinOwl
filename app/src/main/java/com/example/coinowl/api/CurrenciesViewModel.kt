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


    private val rate = MutableLiveData<Float>()

    val currencyOneAmount = MutableLiveData<String>()
    val currencyTwoAmount = MutableLiveData<String>()
    var currencyOne: String = ""
    var currencyTwo: String = ""

    val currencies = MutableLiveData<MutableList<String>>()

    private fun getRate(pair: String) {
        scope.launch {
            val requestResponse = repository.getRate(pair)
            val rateString = requestResponse!!.split(":")[1].replace("}", "")
            rate.postValue(rateString.toFloat())
            setCurrencyOneAmount(currencyOneAmount.value!!)
        }
    }

    fun setCurrencyOneAmount(amount: String) {
        currencyOneAmount.postValue(amount)
        if(rate.value==null) return
        val calc = amount.toFloat() * rate.value!!
        currencyTwoAmount.postValue(String.format("%.2f", calc))
    }

    fun setCurrencyTwoAmount(amount: String) {
        currencyTwoAmount.postValue(amount)
        if(rate.value==null) return
        val calc = amount.toFloat() / rate.value!!
        currencyOneAmount.postValue(String.format("%.2f", calc))
    }

    fun setSymbols(symbol1: String, symbol2: String) {
        currencyOne = symbol1
        currencyTwo = symbol2
        getRate(query())
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

    private fun query(): String =
        currencyOne+"_"+currencyTwo


    fun cancelAllRequests() = coroutineContext.cancel()
}