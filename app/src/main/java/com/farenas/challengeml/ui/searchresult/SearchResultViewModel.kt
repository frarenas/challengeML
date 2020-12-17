package com.farenas.challengeml.ui.searchresult

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.farenas.challengeml.data.repo.ProductDataSource
import com.farenas.challengeml.data.repo.ProductRepository
import com.farenas.challengeml.utils.*

class SearchResultViewModel @ViewModelInject constructor(
    private val productRepository: ProductRepository,
    networkUtils: NetworkUtils?,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val query: String = savedStateHandle[Constants.PARAM_QUERY] ?: ""

    val products = Pager(
        PagingConfig(
            pageSize = Constants.API_ITEMS_LIMIT,
            initialLoadSize = Constants.API_ITEMS_LIMIT,
            prefetchDistance = 10,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { ProductDataSource(productRepository, query) }
    ).flow.cachedIn(viewModelScope)
}