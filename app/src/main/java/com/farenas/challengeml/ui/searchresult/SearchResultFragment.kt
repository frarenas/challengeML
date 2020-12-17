package com.farenas.challengeml.ui.searchresult

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.farenas.challengeml.R
import com.farenas.challengeml.data.model.Product
import com.farenas.challengeml.data.repo.ProductRepository
import com.farenas.challengeml.databinding.FragmentSearchResultBinding
import com.farenas.challengeml.ui.base.BaseFragment
import com.farenas.challengeml.utils.Constants
import com.farenas.challengeml.utils.NetworkUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class SearchResultFragment(
    private val productRepository: ProductRepository
) : BaseFragment<FragmentSearchResultBinding>(R.layout.fragment_search_result) {

    @Inject
    lateinit var productAdapter: ProductAdapter

    @Inject
    lateinit var productLoadStateAdapter: ProductLoadStateAdapter

    @Inject
    lateinit var networkUtils: NetworkUtils

    private val viewModel: SearchResultViewModel by viewModels {
        SearchResultViewModelFactory(productRepository, networkUtils, this, requireArguments())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchResultBinding.bind(view)

        setView()
        observeViewModel()
    }

    private fun setView() {

        binding.rvProducts.apply {
            adapter = productAdapter.withLoadStateFooter(
                footer = productLoadStateAdapter
            )
            layoutManager = LinearLayoutManager(requireActivity())
            addItemDecoration(
                DividerItemDecoration(
                    requireActivity(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }

        productAdapter.setOnClickListener { viewDetails(it) }

        productLoadStateAdapter.setOnClickListener { productAdapter.retry() }

        binding.btnRetry.setOnClickListener { productAdapter.retry() }

        productAdapter.addLoadStateListener { loadState ->

            if (loadState.refresh is LoadState.Loading) {
                binding.pbLoading.isVisible = true
                binding.grpEmptyList.isVisible = false
                binding.rvProducts.isVisible = false
                binding.btnRetry.isVisible = false
            }
            else {
                binding.pbLoading.isVisible = false
                binding.grpEmptyList.isVisible = false
                binding.rvProducts.isVisible = false

                val errorState = when {
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                errorState?.let {
                    if(productAdapter.itemCount == 0) {
                        binding.grpEmptyList.isVisible = true
                        binding.rvProducts.isVisible = false
                        if (it.error.message == Constants.NO_INTERNET) {
                            binding.tvEmptyList.setText(R.string.no_internet)
                            binding.ivEmptyList.setImageResource(R.drawable.ic_no_signal)
                            binding.btnRetry.isVisible = true
                        } else {
                            binding.tvEmptyList.setText(R.string.unknown_error)
                            binding.ivEmptyList.setImageResource(R.drawable.ic_warning)
                            binding.btnRetry.isVisible = false
                        }
                    }else{
                        binding.grpEmptyList.isVisible = false
                        binding.rvProducts.isVisible = true
                    }
                } ?: run {
                    binding.rvProducts.isVisible = true
                    binding.btnRetry.isVisible = false
                }
            }
        }
    }

    private fun observeViewModel(){

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.products.collectLatest {
                productAdapter.submitData(it)
            }
        }
    }

    private fun viewDetails(product: Product) = findNavController().navigate(
        SearchResultFragmentDirections.actionSearchResultFragmentToDetailFragment(product)
    )
}