package com.example.currencyrateconvertingapp

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.currency_item_layout.view.*

class CurrencyAdapter(
    private val context: Context,
    private val onItemClick: ServerViewHolder.OnItemClick
) :
    RecyclerView.Adapter<CurrencyAdapter.ServerViewHolder>() {

    var rateList: List<RateObject> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServerViewHolder {
        val inflatedView =
            LayoutInflater.from(context).inflate(R.layout.currency_item_layout, parent, false)
        return ServerViewHolder(inflatedView)
    }

    fun setList(rateList: List<RateObject>) {
        this.rateList = rateList
    }

    override fun getItemCount() = rateList.size

    override fun onBindViewHolder(holder: ServerViewHolder, position: Int) {
        holder.bind(rateList[position], onItemClick)
    }

    class ServerViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        @SuppressLint("CheckResult")
        fun bind(
            rateObject: RateObject,
            onItemClick: OnItemClick
        ) {
            view.viewTextViewCurrency.text = rateObject.currency
            view.viewTextViewFullCurrencyName.text = rateObject.currencyName
            view.viewEditTextViewValue.setText(String.format("%s", rateObject.amount))
            view.setOnClickListener {
                onItemClick.itemPressed(rateObject)
            }
        }

        interface OnItemClick {
            fun itemPressed(
                rateObject: RateObject
            )
        }
    }
}