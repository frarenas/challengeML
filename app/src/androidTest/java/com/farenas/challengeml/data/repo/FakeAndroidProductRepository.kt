package com.farenas.challengeml.data.repo

import com.farenas.challengeml.data.model.*
import com.farenas.challengeml.MockedObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeAndroidProductRepository : ProductRepository {

    private val products = MockedObject.products.toMutableList()

    private val paging = Paging(1, 1, 1, 1 )
    private val searchResult = SearchResult(paging, products)

    private val observableProducts: Flow<List<Product>> = flow{ emit(products) }

    override suspend fun fetchtProduct(query: String, offset: Int): SearchResult =
        searchResult

    override fun getLastViewedProduct(): Flow<List<Product>> = observableProducts

    override suspend fun saveProduct(product: Product) {
        products.add(product)
    }
}