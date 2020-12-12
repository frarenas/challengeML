package com.farenas.challengeml.data.ui.detail

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.MediumTest
import androidx.test.platform.app.InstrumentationRegistry
import com.farenas.challengeml.MockedObject
import com.farenas.challengeml.R
import com.farenas.challengeml.data.ui.AppFragmentFactoryTest
import com.farenas.challengeml.launchFragmentInHiltContainer
import com.farenas.challengeml.ui.detail.DetailFragment
import com.farenas.challengeml.utils.Constants
import com.farenas.challengeml.utils.TextUtils
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject


@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class DetailFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var fragmentFactoryTest: AppFragmentFactoryTest

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun loadFragment_ShowProductDetails() {

        val product = MockedObject.product1.copy()

        val args = Bundle()
        args.putParcelable(Constants.PARAM_PRODUCT, product)

        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<DetailFragment>(
            fragmentFactory = fragmentFactoryTest,
            fragmentArgs = args
        ){
            Navigation.setViewNavController(requireView(), navController)
        }

        val price = TextUtils.getCurrencyFormat(product.price, product.currencyId)
        val locationFormat = InstrumentationRegistry.getInstrumentation().targetContext.resources.getString(R.string.place_format)
        val location = String.format(locationFormat, product.address!!.cityName, product.address!!.stateName)

        onView(withId(R.id.tv_title)).check(matches(withText(product.title)))
        onView(withId(R.id.tv_price)).check(matches(withText(price)))
        onView(withId(R.id.tv_location)).check(matches(withText(location)))
    }
}