package com.farenas.challengeml.data.model.mapper

import com.farenas.challengeml.data.model.Product
import com.farenas.challengeml.data.model.relation.ProductWithAddressAndAttrs
import javax.inject.Inject

class ProductMapper @Inject constructor() {

    fun mapProductWithAddressAndAttrsToProduct(
            productWithAddressAndAttrs: ProductWithAddressAndAttrs
    ): Product{
        return with(productWithAddressAndAttrs) {
            Product(
                currencyId = this.product.currencyId,
                id = this.product.id,
                price = this.product.price,
                thumbnail = this.product.thumbnail,
                title = this.product.title,
                address = this.address,
                attributes = this.attributes
            )
        }
    }
}