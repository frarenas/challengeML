package com.farenas.challengeml.data.repo

import androidx.room.withTransaction
import com.farenas.challengeml.data.MlApiService
import com.farenas.challengeml.data.db.AddressDao
import com.farenas.challengeml.data.db.AppDataBase
import com.farenas.challengeml.data.db.AttributeDao
import com.farenas.challengeml.data.db.ProductDao
import com.farenas.challengeml.data.model.Product
import com.farenas.challengeml.data.model.SearchResult
import com.farenas.challengeml.data.model.mapper.ProductMapper
import com.farenas.challengeml.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.Exception

class ProductRepositoryImpl(
    private val apiService: MlApiService,
    private val appDataBase: AppDataBase,
    private val productDao: ProductDao,
    private val addressDao: AddressDao,
    private val attributeDao: AttributeDao,
    private val productMapper: ProductMapper
) : ProductRepository {

    override suspend fun fetchtProduct(query: String, offset: Int): SearchResult =
        apiService.getProducts(
            query = query,
            limit = Constants.API_ITEMS_LIMIT,
            offset = offset
        )

    override fun getLastViewedProduct(): Flow<List<Product>> =
        productDao.getProducts().map {
            it.map { item ->
                productMapper.mapProductWithAddressAndAttrsToProduct(item)
            }
        }

    override suspend fun saveProduct(product: Product){
        appDataBase.withTransaction {
            try {
                productDao.upsert(product)
                product.address?.let { addressDao.upsert(it) }
                product.attributes?.let { attributeDao.upsert(it) }
            }catch (e: Exception){}
        }
    }
}