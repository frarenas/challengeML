package com.farenas.challengeml.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.farenas.challengeml.data.model.Product
import com.farenas.challengeml.data.repo.ProductRepository
import com.farenas.challengeml.utils.Event
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel @ViewModelInject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private var _products: MutableLiveData<List<Product>> = MutableLiveData()
    val products: LiveData<List<Product>> = _products

    private var _query: MutableLiveData<Event<String>> = MutableLiveData()
    val query: LiveData<Event<String>> = _query

    init {
        viewModelScope.launch {
            productRepository.getLastViewedProduct().collect {
                _products.value = it
            }
        }
    }

    fun validateSearch(query: String){
        //TODO: check if api require validation
        //https://developers.mercadolibre.com.ar/es_ar/items-y-busquedas
        _query.value = Event(query.trim())
    }
}