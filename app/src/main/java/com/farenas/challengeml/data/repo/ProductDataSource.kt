package com.farenas.challengeml.data.repo

import androidx.paging.PagingSource
import com.farenas.challengeml.data.model.Product
import com.farenas.challengeml.utils.Constants
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException

class ProductDataSource(
    private val productRepository: ProductRepository,
    val query: String
) : PagingSource<Int, Product>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        val offset = params.key ?: 0
        return try {
            val response = productRepository.fetchtProduct(query = query, offset = offset)
            val products = response.products
            products.map { it.address?.productId = it.id }
            products.map { it.attributes?.map { attr -> attr.productId = it.id } }

            LoadResult.Page(
                response.products,
                prevKey = if (offset == 0) null else offset - Constants.API_ITEMS_LIMIT,
                nextKey = if (offset + Constants.API_ITEMS_LIMIT >= response.paging.total){
                    null
                } else{
                    offset + Constants.API_ITEMS_LIMIT
                }
            )
        } catch (exception: UnknownHostException) {
            return LoadResult.Error(Exception(Constants.NO_INTERNET))
        } catch (exception: IOException) {
            return LoadResult.Error(Exception(Constants.NO_INTERNET))
        } catch (exception: HttpException) {
            return LoadResult.Error(Exception(Constants.NO_INTERNET))
        } catch (exception: Exception) {
            return LoadResult.Error(Exception(Constants.UNKNOWN_ERROR))
        }
    }
}