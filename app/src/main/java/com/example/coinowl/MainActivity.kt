package com.example.coinowl

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import java.util.*


// TODO: Implement Card Swapping Animation
// TODO: Add Chart View
// TODO: Remove currency list first item "ALL"
// TODO: Add spinners default selections (eg.: USD -> EUR)
// TODO: Call yesterday currency rate value from API to calc up/down %ratio [formula : (today - yesterday)/100]
// TODO: Set @param main_first_card_indicator & @param main_second_card_indicator TextViews colors according to %ratio
// TODO: Remove @param main_card_sign code implementation as it has been replaced below


class MainActivity : AppCompatActivity(), OnItemSelectedListener {

    private lateinit var currenciesViewModel: CurrenciesViewModel
    private var firstSpinner: AppCompatSpinner? = null
    private var secondSpinner: AppCompatSpinner? = null
    private var firstCurrency: String = "ِ"
    private var secondCurrency: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        currenciesViewModel = ViewModelProviders.of(this).get(CurrenciesViewModel::class.java)
        currenciesViewModel.getCurrencies()

        currenciesViewModel.currencies.observe(this, Observer {
            val spinnerAdapter = ArrayAdapter(this, R.layout.spinner_item, currenciesViewModel.currencies.value)
            spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
            secondSpinner?.adapter = spinnerAdapter
            firstSpinner?.adapter = spinnerAdapter
            updateCurrencyLabel()
            setCurrencyLabel()
        })

        currenciesViewModel.currencyOneAmount.observe(this, Observer {
            main_first_card_amount.setText(currenciesViewModel.currencyOneAmount.value)
        })
        currenciesViewModel.currencyTwoAmount.observe(this, Observer {
            main_second_card_amount.setText(currenciesViewModel.currencyTwoAmount.value)
        })


        firstSpinner = this.main_first_card_spinner
        firstSpinner!!.onItemSelectedListener = this

        secondSpinner = this.main_second_card_spinner
        secondSpinner!!.onItemSelectedListener = this



        main_first_card_amount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                currenciesViewModel.setCurrencyOneAmount(s.toString())
            }
        })
        currenciesViewModel.setCurrencyOneAmount("100")

        main_second_card_amount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                currenciesViewModel.setCurrencyTwoAmount(s.toString())
            }
        })
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        currenciesViewModel.setSymbols(firstSpinner?.selectedItem.toString(), secondSpinner?.selectedItem.toString())
        val curr = Currency.getInstance(firstSpinner?.selectedItem.toString())
        var str: String
        str = if (Build.VERSION.SDK_INT >= 24) curr.getSymbol(Locale.getDefault(Locale.Category.DISPLAY))
        else curr.getSymbol(resources.configuration.locale)
        //main_first_card_currency.text = str
        val curr2 = Currency.getInstance(secondSpinner?.selectedItem.toString())
        var str2: String
        str2 = if (Build.VERSION.SDK_INT >= 24) curr2.getSymbol(Locale.getDefault(Locale.Category.DISPLAY))
        else curr2.getSymbol(resources.configuration.locale)
        //main_second_card_sign.text = str2

        updateCurrencyLabel()
        setCurrencyLabel()

    }

    private fun updateCurrencyLabel() {
        firstCurrency = firstSpinner?.selectedItem.toString()
        secondCurrency = secondSpinner?.selectedItem.toString()

    }

    private fun setCurrencyLabel() {
        main_first_card_currency.text = firstCurrency
        main_second_card_currency.text = secondCurrency
    }


}
