package com.farenas.challengeml.data.repo

import com.farenas.challengeml.data.model.Paging
import com.farenas.challengeml.data.model.Product
import com.farenas.challengeml.data.model.SearchResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeProductRepository : ProductRepository {

    private val products = mutableListOf<Product>()
    private val paging = Paging(1, 1, 1, 1 )
    private val searchResult = SearchResult(paging, products)

    private val observableProducts: Flow<List<Product>> = flow { emit(products)}

    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    override suspend fun fetchtProduct(query: String, offset: Int): SearchResult {
        return if(shouldReturnNetworkError) {
            throw Exception("Error")
        } else {
            searchResult
        }
    }

    override fun getLastViewedProduct(): Flow<List<Product>> = observableProducts

    override suspend fun saveProduct(product: Product) {
        products.add(product)
    }
}