package com.example.currencyrateconvertingapp

import java.math.BigDecimal

data class RateObject (
    val currency: String, var currencyName: String = "", val rateValue: BigDecimal, var amount: BigDecimal = BigDecimal.ZERO
)