package com.farenas.challengeml.data.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.farenas.challengeml.data.repo.ProductRepository
import com.farenas.challengeml.ui.detail.DetailFragment
import com.farenas.challengeml.ui.home.HomeFragment
import com.farenas.challengeml.ui.searchresult.SearchResultFragment
import javax.inject.Inject
import javax.inject.Named

class AppFragmentFactoryTest @Inject constructor() : FragmentFactory() {

    @Inject
    @Named("test_repo")
    lateinit var productRepository: ProductRepository

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className) {
            HomeFragment::class.java.name -> HomeFragment(productRepository)
            SearchResultFragment::class.java.name -> SearchResultFragment(productRepository)
            DetailFragment::class.java.name -> DetailFragment(productRepository)
            else -> super.instantiate(classLoader, className)
        }
    }
}