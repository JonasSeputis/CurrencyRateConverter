package com.example.currencyrateconvertingapp

import com.google.gson.JsonObject
import java.util.*

fun parseRatesObjects(jsonOfRates: JsonObject) : List<RateObject>{
    val rateList = mutableListOf<RateObject>()
    val rateObjects = jsonOfRates.entrySet()
    rateObjects.forEach { rate -> rateList.add(RateObject(rate.key, Currency.getInstance(rate.key).displayName, rate.value.asBigDecimal)) }
    return rateList
}