package com.farenas.challengeml.data.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.farenas.challengeml.MockedObject
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
class AddressDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: AppDataBase
    private lateinit var productDao: ProductDao
    private lateinit var addressDao: AddressDao

    @Before
    fun setup() {
        hiltRule.inject()
        productDao = database.productDao()
        addressDao = database.addressDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insert() = runBlockingTest {

        val product = MockedObject.product1.copy()
        val address = MockedObject.address1.copy()

        productDao.insert(product)
        addressDao.insert(address)

        val addressFromDb = addressDao.getById(address.productId)
        assertThat(addressFromDb).isEqualTo(address)
    }

    @Test
    fun update() = runBlockingTest {

        val product = MockedObject.product1.copy()
        val address = MockedObject.address1.copy()

        productDao.insert(product)
        addressDao.insert(address)

        address.cityName = "San Miguel"

        addressDao.update(address)

        val addressFromDb = addressDao.getById(address.productId)
        assertThat(addressFromDb?.cityName).isEqualTo("San Miguel")
    }

    @Test
    fun upsert() = runBlockingTest {

        val product = MockedObject.product1.copy()
        val address = MockedObject.address1.copy()

        productDao.insert(product)
        addressDao.upsert(address)

        address.cityName = "San Miguel"

        addressDao.upsert(address)

        val addressFromDb = addressDao.getById(address.productId)
        assertThat(addressFromDb?.cityName).isEqualTo("San Miguel")
    }
}