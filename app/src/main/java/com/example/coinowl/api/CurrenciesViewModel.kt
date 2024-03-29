package com.example.coinowl.api

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import org.json.JSONObject
import java.lang.Exception


class CurrenciesViewModel : ViewModel() {
    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default

    private val scope = CoroutineScope(coroutineContext)

    private val repository: CurrenciesRepository =
        CurrenciesRepository(Apifactory.currenciesApisApi)


    private val rate = MutableLiveData<Float>()
    val rates = MutableLiveData<MutableList<Pair<String, Float>>>()

    val currencyOneAmount = MutableLiveData<String>()
    val currencyTwoAmount = MutableLiveData<String>()
    var currencyOne: String = ""
    var currencyTwo: String = ""

    val currencies = MutableLiveData<MutableList<String>>()

    private fun getRate(pair: String) {
        scope.launch {
            try {
                val requestResponse = repository.getRate(pair)
                extractValues(requestResponse!!)
            } catch (e: Exception) {
                Log.d("MyTag", Log.getStackTraceString(e))
            }
        }
    }

    private fun extractValues(got: String) {
        var values = got
            .substring(got.indexOf(":")+1, got.length)
            .replace("{", "")
            .replace("}", "")
            .split(",")
        var mRate = 0f
        val mRates: MutableList<Pair<String, Float>> = mutableListOf()
        values.forEach {
            val pair = it.replace("\"", "").split(":")
            mRate = pair[1].toFloat()
            mRates.add(Pair(pair[0], mRate))
        }
        rate.postValue(mRate)
        rates.postValue(mRates)
        setCurrencyOneAmount(currencyOneAmount.value!!)
    }

    fun setCurrencyOneAmount(amount: String) {
        if(rate.value==null) return
        val calc = amount.toFloat() * rate.value!!
        currencyTwoAmount.postValue(String.format("%.2f", calc))
    }

    fun setCurrencyTwoAmount(amount: String) {
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