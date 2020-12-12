package com.farenas.challengeml.data.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.farenas.challengeml.MockedObject
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class ProductDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: AppDataBase
    private lateinit var dao: ProductDao

    @Before
    fun setup() {
        hiltRule.inject()
        dao = database.productDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insert() = runBlockingTest {

        val product = MockedObject.product1.copy()

        dao.insert(product)
        val productFromDb = dao.getById(product.id)

        assertThat(productFromDb).isNotNull()
    }

    @Test
    fun update() = runBlockingTest {

        val product = MockedObject.product1.copy()

        dao.insert(product)
        product.price = 1.0
        dao.update(product)
        val productFromDb = dao.getById(product.id)

        assertThat(productFromDb?.price).isEqualTo(1.0)
    }

    @Test
    fun getProducts() = runBlockingTest {

        val product1 = MockedObject.product1.copy()
        val product2 = MockedObject.product2.copy()
        val product3 = MockedObject.product3.copy()

        val job = async { dao.getProducts().take(3).toList() }

        dao.insert(product1)
        dao.insert(product2)
        dao.insert(product3)

        val products = job.await()

        assertThat(products.count()).isEqualTo(3)
    }

    @Test
    fun getById() = runBlockingTest {
        val product = MockedObject.product1.copy()

        dao.insert(product)
        val productFromDb = dao.getById(product.id)

        assertThat(productFromDb).isNotNull()
    }
}