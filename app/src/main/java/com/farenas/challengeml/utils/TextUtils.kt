package com.farenas.challengeml.utils

import java.text.NumberFormat
import java.util.*

class TextUtils {
    companion object {
        fun getCurrencyFormat(value: Double, currencyCode: String): String{
            val format = NumberFormat.getCurrencyInstance()
            format.minimumFractionDigits = 2
            format.maximumFractionDigits = 2

            format.currency = try {
                Currency.getInstance(currencyCode)
            }catch (e: IllegalArgumentException){
                Currency.getInstance(Currency.getInstance(Locale.getDefault()).currencyCode)
            }

            return format.format(value)
        }
    }
}