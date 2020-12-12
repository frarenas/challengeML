package com.farenas.challengeml.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.farenas.challengeml.MainCoroutineRule
import com.farenas.challengeml.data.repo.FakeProductRepository
import com.farenas.challengeml.getOrAwaitValueTest
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: HomeViewModel
    private lateinit var fakeProductRepository: FakeProductRepository

    @Before
    fun setup() {
        fakeProductRepository = FakeProductRepository()
        viewModel = HomeViewModel(fakeProductRepository)
    }

    @Test
    fun `validate valid query with spaces, returns same query`() {
        viewModel.validateSearch("qwerty")
        val value = viewModel.query.getOrAwaitValueTest().peekContent()
        assertThat(value).isEqualTo("qwerty")
    }

    @Test
    fun `validate query with spaces, returns trimmed query`() {
        viewModel.validateSearch("    qwerty      ")
        val value = viewModel.query.getOrAwaitValueTest().peekContent()
        assertThat(value).isEqualTo("qwerty")
    }
}