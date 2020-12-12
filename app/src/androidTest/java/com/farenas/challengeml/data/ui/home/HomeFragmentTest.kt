package com.farenas.challengeml.data.ui.home

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.EditorAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.farenas.challengeml.MockedObject
import com.farenas.challengeml.R
import com.farenas.challengeml.data.ui.AppFragmentFactoryTest
import com.farenas.challengeml.launchFragmentInHiltContainer
import com.farenas.challengeml.ui.home.HomeFragment
import com.farenas.challengeml.ui.home.HomeFragmentDirections
import com.farenas.challengeml.ui.home.LastViewedProductAdapter
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
class HomeFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var fragmentFactoryTest: AppFragmentFactoryTest

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun searchProduct_navigateToProductsFragment() {
        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<HomeFragment>(fragmentFactory = fragmentFactoryTest){
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.et_search)).perform(ViewActions.replaceText("product"))
        onView(withId(R.id.et_search)).perform(EditorAction())

        Mockito.verify(navController).navigate(
            HomeFragmentDirections.actionHomeFragmentToSearchResultFragment("product")
        )
    }

    @Test
    fun clickLastViewedProduct_navigateToDetailFragment() {

        val products = MockedObject.products.toList()

        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<HomeFragment>(fragmentFactory = fragmentFactoryTest){
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.rv_products)).perform(
            RecyclerViewActions.actionOnItemAtPosition<LastViewedProductAdapter.ViewHolder>(
                0,
                ViewActions.click()
            )
        )

        Mockito.verify(navController).navigate(
            HomeFragmentDirections.actionHomeFragmentToDetailFragment(products[0])
        )
    }
}