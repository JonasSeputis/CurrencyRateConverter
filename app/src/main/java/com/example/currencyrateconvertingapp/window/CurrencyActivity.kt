package com.example.currencyrateconvertingapp.window

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.currencyrateconvertingapp.*
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import kotlinx.android.synthetic.main.currency_activity_layout.*
import kotlinx.android.synthetic.main.currency_item_layout.view.*
import timber.log.Timber
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

class CurrencyActivity : AppCompatActivity(),
    CurrencyAdapter.ServerViewHolder.OnItemClick {

    private val DEFAULT_CURRENCY_RATE = "0"
    private val DEFAULT_CURRENCY = "EUR"

    lateinit var viewModel: CurrencyViewModel
    private val rateAdapter = CurrencyAdapter(this, this)
    var ratesList = emptyList<RateObject>()
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.currency_activity_layout)
        val toolbar = viewToolbarView as Toolbar
        setSupportActionBar(toolbar)
        viewRecyclerView.layoutManager = LinearLayoutManager(this)
        viewRecyclerView.adapter = rateAdapter

        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
            .create(CurrencyViewModel::class.java)
        viewModel.retrieveRates(DEFAULT_CURRENCY)
        viewBaseCurrencyView.viewEditTextViewValue.setText(DEFAULT_CURRENCY_RATE)
        observeViewModel(viewModel)
    }

    @SuppressLint("CheckResult")
    private fun observeViewModel(viewModel: CurrencyViewModel) {
        compositeDisposable.add(
            Observable.combineLatest(
                viewModel.getRatesObservable(),
                viewBaseCurrencyView.viewEditTextViewValue.textChanges(),
                BiFunction<CurrencyObject, CharSequence, List<RateObject>> { currency, text ->
                    viewBaseCurrencyView.viewTextViewCurrency.text = currency.baseCurrency
                    viewBaseCurrencyView.viewTextViewFullCurrencyName.text =
                        Currency.getInstance(currency.baseCurrency).displayName

                    ratesList = parseRatesObjects(currency.rates)
                    val rateValue = if (text.toString().isNotEmpty()) {
                        text.toString().toBigDecimal()
                    } else {
                        BigDecimal.ZERO
                    }

                    ratesList.forEach { rate ->
                        ratesList[ratesList.indexOf(rate)].amount =
                            rate.rateValue.multiply(rateValue).setScale(2, RoundingMode.CEILING)
                    }
                    ratesList
                }
            )
                .subscribe({
                    rateAdapter.setList(ratesList)
                    rateAdapter.notifyDataSetChanged()
                }, { t: Throwable? ->
                    Timber.e(t)
                })
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        clearDisposable()
    }

    override fun onPause() {
        super.onPause()
        clearDisposable()
    }

    override fun onResume() {
        super.onResume()
        viewModel.retrieveRates(DEFAULT_CURRENCY)
        observeViewModel(viewModel)
    }

    private fun clearDisposable() {
        compositeDisposable.clear()
    }

    override fun itemPressed(
        rateObject: RateObject
    ) {
        clearDisposable()
        viewBaseCurrencyView.viewEditTextViewValue.setText(String.format("%s", rateObject.amount))
        viewModel.retrieveRates(rateObject.currency)
        observeViewModel(viewModel)
    }
}
