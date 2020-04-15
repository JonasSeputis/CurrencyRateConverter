package com.example.currencyrateconvertingapp.network

import com.example.currencyrateconvertingapp.CurrencyObject
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface Api {

    @GET("/api/android/latest")
    @Headers("Content-type: application/json")
    fun receiveRates(@Query("base") currency: String): Observable<CurrencyObject>

}