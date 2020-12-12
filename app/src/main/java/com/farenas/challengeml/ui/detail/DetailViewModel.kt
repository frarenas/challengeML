package com.farenas.challengeml.ui.detail

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farenas.challengeml.data.model.Product
import com.farenas.challengeml.data.repo.ProductRepository
import com.farenas.challengeml.utils.Constants
import kotlinx.coroutines.launch
import java.util.*

class DetailViewModel @ViewModelInject constructor(
        private val productRepository: ProductRepository,
        @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    init {
        viewModelScope.launch {
            val product = savedStateHandle.get<Product>(Constants.PARAM_PRODUCT)
            product?.let {
                it.lastViewed = Date()
                productRepository.saveProduct(it)
            }
        }
    }
}