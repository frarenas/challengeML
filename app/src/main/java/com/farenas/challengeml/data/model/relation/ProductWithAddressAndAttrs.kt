package com.farenas.challengeml.data.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.farenas.challengeml.data.model.Address
import com.farenas.challengeml.data.model.Attribute
import com.farenas.challengeml.data.model.Product

data class ProductWithAddressAndAttrs(

    @Embedded val product: Product,

    @Relation(
        parentColumn = "id",
        entityColumn = "productId"
    )
    val address: Address?,

    @Relation(
        parentColumn = "id",
        entityColumn = "productId"
    )
    val attributes: List<Attribute>?
)