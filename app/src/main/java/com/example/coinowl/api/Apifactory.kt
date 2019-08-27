package com.example.coinowl.api

import android.util.Log
import com.example.coinowl.Secrets
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object Apifactory {

    private val authInterceptor = Interceptor {chain->
        val newUrl = chain.request().url()
            .newBuilder()
            .addQueryParameter("apiKey", Secrets.apiKey)
            .build()
        Log.d("MyTag", newUrl.toString())
        val newRequest = chain.request()
            .newBuilder()
            .url(newUrl)
            .build()

        chain.proceed(newRequest)
    }

    private val client = OkHttpClient().newBuilder()
        .addInterceptor(authInterceptor)
        .build()

    fun retrofit() : Retrofit {
        val builder = Retrofit.Builder()
            .client(client)
            .baseUrl("https://free.currconv.com/api/v7/")
            .addCallAdapterFactory(CoroutineCallAdapterFactory())

        //builder.addConverterFactory(MoshiConverterFactory.create())
        builder.addConverterFactory(ScalarsConverterFactory.create())
        return  builder.build()
    }

    val currenciesApisApi: CurrenciesApi =  retrofit().create(CurrenciesApi::class.java)
}