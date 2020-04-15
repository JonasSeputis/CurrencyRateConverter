package com.example.currencyrateconvertingapp.window

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.currencyrateconvertingapp.CurrencyObject
import com.example.currencyrateconvertingapp.repository.CurrencyRepository
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class CurrencyViewModel(application: Application) : AndroidViewModel(application) {

    private var ratesObservable: Observable<CurrencyObject> = Observable.empty()


    fun retrieveRates(currency: String) {
        ratesObservable = Observable.interval(0,1, TimeUnit.SECONDS).flatMap {
            return@flatMap CurrencyRepository.getInstance().getRates(currency)
        }
    }

    fun getRatesObservable(): Observable<CurrencyObject> {
        return ratesObservable
    }

}