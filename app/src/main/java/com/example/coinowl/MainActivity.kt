package com.example.coinowl

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.coinowl.api.CurrenciesViewModel
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.ArrayAdapter
import android.widget.Toast
import android.widget.AdapterView.OnItemSelectedListener



//@todo #2 Implement Menu Inflater method to the ImageView holding burger icon

class MainActivity : AppCompatActivity() {
    private lateinit var currenciesViewModel: CurrenciesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    /*    currenciesViewModel = ViewModelProviders.of(this).get(CurrenciesViewModel::class.java)
        currenciesViewModel.response.observe(this, Observer{
            textview.text = currenciesViewModel.response.value
        })
        currenciesViewModel.currencies.observe(this, Observer {
            val dataAdapter = ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item, currenciesViewModel.currencies.value
            )
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner2.adapter = dataAdapter
            spinner1.adapter = dataAdapter
        })

        currenciesViewModel.getRate("USD_PHP")
        currenciesViewModel.getCurrencies()

        submit.setOnClickListener {
            currenciesViewModel.getRate(query())
        }*/
    }

    //fun query(): String = spinner1.selectedItem.toString()+"_"+spinner2.selectedItem.toString()
}
