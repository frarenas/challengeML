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
class AttributeDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: AppDataBase
    private lateinit var productDao: ProductDao
    private lateinit var attributeDao: AttributeDao

    @Before
    fun setup() {
        hiltRule.inject()
        productDao = database.productDao()
        attributeDao = database.attibuteDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insert() = runBlockingTest {

        val product = MockedObject.product1.copy()
        val attribute = MockedObject.attribute1.copy()

        productDao.insert(product)
        attributeDao.insert(attribute)

        val attributeFromDb = attributeDao.get(attribute.productId, attribute.name)
        assertThat(attributeFromDb).isEqualTo(attribute)
    }

    @Test
    fun update() = runBlockingTest {

        val product = MockedObject.product1.copy()
        val attribute = MockedObject.attribute1.copy()

        productDao.insert(product)
        attributeDao.insert(attribute)

        attribute.value = "Marca Blanca"

        attributeDao.update(attribute)

        val attributeFromDb = attributeDao.get(attribute.productId, attribute.name)
        assertThat(attributeFromDb?.value).isEqualTo("Marca Blanca")
    }

    @Test
    fun upsert() = runBlockingTest {

        val product = MockedObject.product1.copy()
        val attributes = MockedObject.attributes1.toList()

        productDao.insert(product)
        attributeDao.upsert(attributes)

        attributes[1].value = "2021"

        attributeDao.upsert(attributes)

        val attributeFromDb = attributeDao.get(attributes[0].productId, attributes[0].name)
        val attribute2FromDb = attributeDao.get(attributes[1].productId, attributes[1].name)
        assertThat(attributeFromDb?.value).isEqualTo("Acme")
        assertThat(attribute2FromDb?.value).isEqualTo("2021")
    }
}