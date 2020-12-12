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
    fun formatCurrency(){
        val currency = TextUtils.getCurrencyFormat(1000000.toDouble(), "ARS")
        assertEquals("$1.000.000", currency)
    }
}