package com.farenas.challengeml.data.repo

import com.farenas.challengeml.data.model.Product
import com.farenas.challengeml.data.model.SearchResult
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun fetchtProduct(query: String, offset: Int): SearchResult
    fun getLastViewedProduct(): Flow<List<Product>>
    suspend fun saveProduct(product: Product)
}