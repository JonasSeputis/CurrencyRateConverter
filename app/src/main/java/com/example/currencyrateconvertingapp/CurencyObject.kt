package com.example.currencyrateconvertingapp

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class CurrencyObject (
    @SerializedName("baseCurrency")
    val baseCurrency: String,

    @SerializedName("rates")
    var rates: JsonObject
)