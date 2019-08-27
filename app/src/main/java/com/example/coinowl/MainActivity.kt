package com.example.coinowl

import android.graphics.Color
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
import androidx.core.content.ContextCompat
import com.example.coinowl.Utils.CurrencyMarkerView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.Utils
import java.util.*
import kotlin.collections.ArrayList


// TODO: Implement Card Swapping Animation
// TODO: Add Chart View
// TODO: Add second spinner default selection different from the first one(eg.: USD -> EUR)
// TODO: Call yesterday currency rate value from API to calc up/down %ratio [formula : (today - yesterday)/100]
// TODO: Set @param main_first_card_indicator & @param main_second_card_indicator TextViews colors according to %ratio
// TODO: Remove @param main_card_sign code implementation as it has been replaced below


class MainActivity : AppCompatActivity(), OnItemSelectedListener {

    private lateinit var currenciesViewModel: CurrenciesViewModel
    private var firstSpinner: AppCompatSpinner? = null
    private var secondSpinner: AppCompatSpinner? = null
    private var firstCurrency: String = "ِ"
    private var secondCurrency: String = ""
    private lateinit var mChart: LineChart
    private var values: ArrayList<Entry> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupChartView()
        setData()
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


        currenciesViewModel.rates.observe(this, Observer {
            values.clear()
            it.forEachIndexed { index, pair ->
                Log.d("MyTag", pair.toString())
                values.add(Entry(index.toFloat(), pair.second))
            }
            setData()
        })



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

    private fun setupChartView() {
        val markerView = CurrencyMarkerView(this, R.layout.chart_marker_view)

        mChart = findViewById(R.id.chart)
        mChart.setTouchEnabled(true)
        mChart.setPinchZoom(true)

        //Remove Labels
        mChart.description.text = ""
        mChart.legend.isEnabled = false

        markerView.chartView = mChart
        mChart.marker = markerView

        mChart.setDrawGridBackground(false)

        //Set Animation
        mChart.animateX(1000)
        mChart.isDragEnabled = true
        mChart.setScaleEnabled(true)
        mChart.setTouchEnabled(true)

        mChart.isHighlightPerDragEnabled = true
        mChart.isHighlightPerTapEnabled = true

        //Remove all Guidelines
        mChart.xAxis.setDrawGridLines(false)
        mChart.axisLeft.setDrawGridLines(false)
        mChart.axisRight.setDrawGridLines(false)
        mChart.axisLeft.setDrawAxisLine(false)
        mChart.axisLeft.setDrawLabels(false)
        mChart.axisRight.setDrawAxisLine(false)
        mChart.axisRight.setDrawLabels(false)
        mChart.xAxis.setDrawLabels(false)
        mChart.xAxis.isEnabled = false

    }


    private fun setData() {
        val set1: LineDataSet
        if (mChart.data != null && mChart.data.dataSetCount > 0) {
            set1 = mChart.data.getDataSetByIndex(0) as LineDataSet
            set1.values = values
            mChart.data.notifyDataChanged()
            mChart.notifyDataSetChanged()

        } else {
            set1 = LineDataSet(values, "Currency Values")
            set1.setDrawIcons(false)
            set1.setDrawCircles(false)
            set1.setDrawValues(false)
            set1.color = resources.getColor(R.color.colorPrimary)
            set1.lineWidth = 1f
            set1.setDrawCircleHole(false)
            set1.valueTextSize = 9f
            set1.setDrawFilled(true)
            set1.mode = LineDataSet.Mode.CUBIC_BEZIER

            if (Utils.getSDKInt() >= 18) {
                val drawable = ContextCompat.getDrawable(this, R.drawable.chart_gradient)
                set1.fillDrawable = drawable
            } else {
                set1.fillColor = Color.DKGRAY
            }
            val dataSets = ArrayList<ILineDataSet>()
            dataSets.add(set1)
            val data = LineData(dataSets)
            mChart.data = data
        }
        mChart.invalidate()
    }

    private fun generateRandomValue(): Int {
        val random = Random()
        return random.nextInt(101)
    }


}
