package com.farenas.challengeml.data.ui.searchresult

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.MediumTest
import com.farenas.challengeml.MockedObject
import com.farenas.challengeml.R
import com.farenas.challengeml.data.ui.AppFragmentFactoryTest
import com.farenas.challengeml.launchFragmentInHiltContainer
import com.farenas.challengeml.ui.searchresult.ProductAdapter
import com.farenas.challengeml.ui.searchresult.SearchResultFragment
import com.farenas.challengeml.ui.searchresult.SearchResultFragmentDirections
import com.farenas.challengeml.utils.Constants
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
class SearchResultFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var fragmentFactoryTest: AppFragmentFactoryTest

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun clickProduct_navigateToDetailFragment() {

        val products = MockedObject.products.toList()

        val navController = Mockito.mock(NavController::class.java)

        val args = Bundle()
        args.putString(Constants.PARAM_QUERY, "test")

        launchFragmentInHiltContainer<SearchResultFragment>(
            fragmentFactory = fragmentFactoryTest,
            fragmentArgs = args
        ){
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(ViewMatchers.withId(R.id.rv_products)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ProductAdapter.ViewHolder>(
                1,
                ViewActions.click()
            )
        )

        Mockito.verify(navController).navigate(
            SearchResultFragmentDirections.actionSearchResultFragmentToDetailFragment(products[1])
        )
    }
}