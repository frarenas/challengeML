package com.farenas.challengeml.data.model.mapper

import com.farenas.challengeml.data.model.Address
import com.farenas.challengeml.data.model.Attribute
import com.farenas.challengeml.data.model.Product
import com.farenas.challengeml.data.model.relation.ProductWithAddressAndAttrs
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class ProductMapperTest {

    private lateinit var productMapper: ProductMapper

    @Before
    fun setup(){
        productMapper = ProductMapper()
    }

    @Test
    fun `map product from db with relations to product entity`(){
        val product = Product(
            null,
            null,
            "ARS",
            "00000000",
            0.0,
            "https://example.com",
            "Product")

        val address = Address(
            "Capital Federal",
            "Buenos Aires",
            "00000000")

        val attribute = Attribute(
            "Marca",
            "Acme",
            "00000000")

        val attribute2 = Attribute(
            "Año",
            "2020",
            "00000000")

        val productWithAddressAndAttrs = ProductWithAddressAndAttrs(
            product,
            address,
            listOf(attribute, attribute2)
        )

        val productMapped = productMapper.mapProductWithAddressAndAttrsToProduct(productWithAddressAndAttrs)

        assertThat(productMapped.id).isEqualTo(product.id)
        assertThat(productMapped.address?.cityName).isEqualTo(address.cityName)
        assertThat(productMapped.attributes?.get(0)?.value).isEqualTo(attribute.value)
    }
}