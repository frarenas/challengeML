package com.farenas.challengeml.utils

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.*

class TextUtilsTest{

    @Before
    fun setup(){
        Locale.setDefault(Locale("es", "AR"))
    }

    @Test
    fun `format local currency, returns correct locate format`(){
        val currency = TextUtils.getCurrencyFormat(1.toDouble(), "ARS")
        assertEquals("$1,00", currency)
    }

    @Test
    fun `format foreign currency, returns correct currency simbol`(){
        val currency = TextUtils.getCurrencyFormat(1.toDouble(), "USD")
        assertEquals("US$1,00", currency)
    }

    @Test
    fun `format big currency, returns string with separator every three digit`(){
        val currency = TextUtils.getCurrencyFormat(1000000.toDouble(), "ARS")
        assertEquals("$1.000.000,00", currency)
    }

    @Test
    fun `format currency one decimal, returns string formated with two decimals`(){
        val currency = TextUtils.getCurrencyFormat(10.1, "ARS")
        assertEquals("$10,10", currency)
    }

    @Test
    fun `format currency with many decimals, returns string formated with two decimals rounded`(){
        val currency = TextUtils.getCurrencyFormat(10.106238923, "ARS")
        assertEquals("$10,11", currency)
    }
}