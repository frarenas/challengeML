package com.farenas.challengeml.ui.home

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.farenas.challengeml.R
import com.farenas.challengeml.data.model.Product
import com.farenas.challengeml.data.repo.ProductRepository
import com.farenas.challengeml.databinding.FragmentHomeBinding
import com.farenas.challengeml.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment(
    private val productRepository: ProductRepository
) : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    @Inject
    lateinit var lastViewedProductAdapter: LastViewedProductAdapter

    private val viewModel: HomeViewModel by viewModels {
        HomeViewModelFactory(productRepository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        setView()
        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
    }

    private fun setView(){

        binding.etSearch.setOnEditorActionListener(OnEditorActionListener { _, actionId, _ ->
            val input = binding.etSearch.text.toString()

            if (actionId == EditorInfo.IME_ACTION_SEARCH && input.isNotBlank()) {
                viewModel.validateSearch(input)
                return@OnEditorActionListener true
            }
            false
        })

        binding.rvProducts.apply {
            adapter = lastViewedProductAdapter
            layoutManager = LinearLayoutManager(
                requireActivity(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
            PagerSnapHelper().attachToRecyclerView(this)
        }

        lastViewedProductAdapter.setOnClickListener {
            viewDetails(it)
        }
    }

    private fun observeViewModel() {
        viewModel.products.observe(viewLifecycleOwner, {
            binding.apply {
                pbLoading.visibility = View.GONE
                if (it.isNullOrEmpty()) {
                    grpLastViewed.visibility = View.GONE
                    grpEmptyList.visibility = View.VISIBLE
                } else {
                    grpLastViewed.visibility = View.VISIBLE
                    grpEmptyList.visibility = View.GONE
                    lastViewedProductAdapter.submitList(ArrayList(it))
                }
            }
        })

        viewModel.query.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let { result ->
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToSearchResultFragment(result)
                )
            }
        })
    }

    private fun viewDetails(product: Product) {
        hideKeyboard()
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToDetailFragment(product)
        )
    }

    private fun hideKeyboard() {
        val inputManager: InputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(
            requireActivity().currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS
        )
    }
}