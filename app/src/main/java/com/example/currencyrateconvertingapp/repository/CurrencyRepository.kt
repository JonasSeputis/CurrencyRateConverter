package com.example.currencyrateconvertingapp.repository

import com.example.currencyrateconvertingapp.CurrencyObject
import com.example.currencyrateconvertingapp.network.Network
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CurrencyRepository {

    companion object {
        fun getInstance() = CurrencyRepository()
    }

    fun getRates(currency: String): Observable<CurrencyObject> {
        return Network().getApi().receiveRates(currency)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}