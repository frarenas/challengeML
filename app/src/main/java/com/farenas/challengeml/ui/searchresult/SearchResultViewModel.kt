package com.farenas.challengeml.ui.searchresult

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.farenas.challengeml.data.model.Product
import com.farenas.challengeml.data.repo.ProductRepository
import com.farenas.challengeml.utils.*
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class SearchResultViewModel @ViewModelInject constructor(
    private val productRepository: ProductRepository,
    private val networkUtils: NetworkUtils?,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val query: String = savedStateHandle[Constants.PARAM_QUERY] ?: ""
    private var fetchedProducts = mutableListOf<Product>()
    private var offset = 0

    private var _products: MutableLiveData<Resource<List<Product>>> = MutableLiveData()
    val products: LiveData<Resource<List<Product>>> = _products

    private var _resultCount: MutableLiveData<String> = MutableLiveData()
    val resultCount: LiveData<String> = _resultCount

    private var _loadMoreItems: MutableLiveData<Boolean> = MutableLiveData()
    val loadMoreItems: LiveData<Boolean> = _loadMoreItems

    init {
        getProducts()
    }

    fun getProducts(){
        if(networkUtils == null || networkUtils.isNetworkConnected()) {
            _products.postValue(Resource.loading(fetchedProducts))
            viewModelScope.launch {
                try {
                    val searchResult = productRepository.fetchtProduct(query, offset)
                    _resultCount.postValue(searchResult.paging.total.toString())

                    val products = searchResult.products
                    products.map { it.address?.productId = it.id }
                    products.map { it.attributes?.map { attr -> attr.productId = it.id } }
                    fetchedProducts.addAll(products)

                    _products.postValue(Resource.success(fetchedProducts))

                    offset += Constants.API_ITEMS_LIMIT
                    _loadMoreItems.postValue(offset < searchResult.paging.total)

                } catch (e: UnknownHostException) {
                    _products.postValue(Resource.error(Constants.NO_INTERNET, fetchedProducts))
                } catch (e: SocketTimeoutException) {
                    _products.postValue(Resource.error(Constants.NO_INTERNET, fetchedProducts))
                }catch (e: Exception) {
                    _products.postValue(Resource.error(Constants.UNKNOWN_ERROR, fetchedProducts))
                }
            }
        }else{
            _products.postValue(Resource.error(Constants.NO_INTERNET, fetchedProducts))
        }
    }
}