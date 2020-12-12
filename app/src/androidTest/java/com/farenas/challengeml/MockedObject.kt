package com.farenas.challengeml

import com.farenas.challengeml.data.model.Address
import com.farenas.challengeml.data.model.Attribute
import com.farenas.challengeml.data.model.Product

object MockedObject {

    val address1 = Address(
        "Capital Federal",
        "Buenos Aires",
        "00000001"
    )

    private val address2 = Address(
        "Rosario",
        "Santa Fe",
        "00000002"
    )

    val attribute1 = Attribute(
        "Marca",
        "Acme",
        "00000001"
    )

    private val attribute2 = Attribute(
        "Año",
        "2020",
        "00000001"
    )

    private val attribute3 = Attribute(
        "Marca",
        "Acme",
        "00000002"
    )

    private val attribute4 = Attribute(
        "Año",
        "2019",
        "00000002"
    )

    val attributes1 = listOf(
        attribute1,
        attribute2
    )

    private val attributes2 = listOf(
        attribute3,
        attribute4
    )

    val product1 = Product(
        address1,
        attributes1,
        "ARS",
        "00000001",
        1.0,
        "https://example.com",
        "Product 1"
    )

    val product2 = Product(
        address2,
        attributes2,
        "USD",
        "00000002",
        1.0,
        "https://example.com",
        "Product 2"
    )

    val product3 = Product(
        address2,
        attributes2,
        "ARS",
        "00000003",
        1.0,
        "https://example.com",
        "Product 3"
    )

    val products = mutableListOf(
        product1,
        product2,
        product3
    )
}