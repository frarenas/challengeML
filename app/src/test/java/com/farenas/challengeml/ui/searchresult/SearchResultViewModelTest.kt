package com.farenas.challengeml.ui.searchresult

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.farenas.challengeml.MainCoroutineRule
import com.farenas.challengeml.data.repo.FakeProductRepository
import com.farenas.challengeml.getOrAwaitValueTest
import com.farenas.challengeml.utils.Constants
import com.farenas.challengeml.utils.NetworkUtils
import com.farenas.challengeml.utils.Status
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`


@ExperimentalCoroutinesApi
class SearchResultViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: SearchResultViewModel
    private lateinit var fakeProductRepository: FakeProductRepository
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var networkUtils: NetworkUtils

    @Before
    fun setup() {
        fakeProductRepository = FakeProductRepository()
        savedStateHandle = SavedStateHandle()
        networkUtils = Mockito.mock(NetworkUtils::class.java)

    }

    @Test
    fun `fetch products, returns success`() {
        `when`(networkUtils.isNetworkConnected()).thenReturn(true)
        viewModel = SearchResultViewModel(fakeProductRepository, networkUtils, savedStateHandle)
        val value = viewModel.products.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.SUCCESS)
    }

    @Test
    fun `fetch products, returns error`() {
        `when`(networkUtils.isNetworkConnected()).thenReturn(true)
        fakeProductRepository.setShouldReturnNetworkError(true)
        viewModel = SearchResultViewModel(fakeProductRepository, networkUtils, savedStateHandle)

        val value = viewModel.products.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)
        assertThat(value.message).isEqualTo(Constants.UNKNOWN_ERROR)
    }

    @Test
    fun `fetch products no connection, returns error`() {
        `when`(networkUtils.isNetworkConnected()).thenReturn(false)
        viewModel = SearchResultViewModel(fakeProductRepository, networkUtils, savedStateHandle)

        val value = viewModel.products.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)
        assertThat(value.message).isEqualTo(Constants.NO_INTERNET)
    }
}