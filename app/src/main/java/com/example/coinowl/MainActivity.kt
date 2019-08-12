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
import androidx.appcompat.widget.AppCompatSpinner


// @todo #2 Un-comment old code to wire up with new UI
// @todo #3 Remove @param testCurrencyList after wire up
// @todo #4 Implement Card Swapping Animation
// @todo #5 Add Chart View

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var currenciesViewModel: CurrenciesViewModel
    private var firstSpinner: AppCompatSpinner? = null
    private var secondSpinner: AppCompatSpinner? = null

    private var testCurrencyList = arrayOf(
        "American Dollar",
        "Euro",
        "Egyptian Pound",
        "Australian Dollar",
        "Kuwaiti Dinar",
        "British Pound",
        "Russian Ruble"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spinnerAdapter = ArrayAdapter(this, R.layout.spinner_item, testCurrencyList)
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        firstSpinner = this.main_first_card_spinner
        firstSpinner!!.onItemSelectedListener = this
        firstSpinner!!.adapter = spinnerAdapter

        secondSpinner = this.main_second_card_spinner
        secondSpinner!!.onItemSelectedListener = this
        secondSpinner!!.adapter = spinnerAdapter
        secondSpinner!!.setSelection(1)

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

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Log.i("Statuss", "Done");
    }
}
