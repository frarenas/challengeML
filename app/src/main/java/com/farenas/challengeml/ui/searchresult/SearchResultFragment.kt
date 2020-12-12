package com.farenas.challengeml.ui.searchresult

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.farenas.challengeml.R
import com.farenas.challengeml.data.model.Product
import com.farenas.challengeml.data.repo.ProductRepository
import com.farenas.challengeml.databinding.FragmentSearchResultBinding
import com.farenas.challengeml.ui.base.BaseFragment
import com.farenas.challengeml.utils.Constants
import com.farenas.challengeml.utils.NetworkUtils
import com.farenas.challengeml.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SearchResultFragment(
    private val productRepository: ProductRepository
) : BaseFragment<FragmentSearchResultBinding>(R.layout.fragment_search_result) {

    @Inject
    lateinit var productAdapter: ProductAdapter

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
            adapter = productAdapter
            layoutManager = LinearLayoutManager(requireActivity())
            addItemDecoration(
                DividerItemDecoration(
                    requireActivity(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }

        productAdapter.setOnClickListener {
            viewDetails(it)
        }

        binding.btnRetry.setOnClickListener {
            viewModel.getProducts()
        }

        binding.btnLoadingMoreRetry.setOnClickListener {
            viewModel.getProducts()
        }
    }

    private fun observeViewModel(){
        viewModel.products.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> handleSuccess(it.data)
                Status.ERROR -> handleError(it.message ?: Constants.UNKNOWN_ERROR, it.data)
                Status.LOADING -> handleLoading(it.data)
            }
            it.data?.let { data -> productAdapter.submitList(ArrayList(data)) }
        })

        viewModel.resultCount.observe(viewLifecycleOwner, {
            binding.tvResultCount.text = String.format(getString(R.string.results_count), it)
        })

        viewModel.loadMoreItems.observe(viewLifecycleOwner, {
            if (it) {
                binding.rvProducts.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                        super.onScrollStateChanged(recyclerView, newState)
                        if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                            recyclerView.clearOnScrollListeners()
                            viewModel.getProducts()
                        }
                    }
                })
            }
        })
    }

    private fun viewDetails(product: Product) = findNavController().navigate(
        SearchResultFragmentDirections.actionSearchResultFragmentToDetailFragment(product)
    )

    private fun handleLoading(data: List<Product>?){
        binding.apply {
            btnRetry.visibility = View.GONE
            if(data.isNullOrEmpty()){
                grpEmptyList.visibility = View.GONE
                pbLoading.visibility = View.VISIBLE
                grpSearchResult.visibility = View.GONE
            }else{
                llLoadingMore.visibility = View.VISIBLE
                grpSearchResult.visibility = View.VISIBLE
            }
        }
    }

    private fun handleSuccess(data: List<Product>?){
        binding.apply {
            pbLoading.visibility = View.GONE
            btnRetry.visibility = View.GONE
            if (data.isNullOrEmpty()) {
                grpSearchResult.visibility = View.GONE
                grpEmptyList.visibility = View.VISIBLE
                tvEmptyList.text = getText(R.string.no_result_found)
                ivEmptyList.setImageResource(R.drawable.ic_warning)
            } else {
                pbLoading.visibility = View.GONE
                llLoadingMore.visibility = View.GONE
                llLoadingMoreError.visibility = View.GONE
                grpEmptyList.visibility = View.GONE
                grpSearchResult.visibility = View.VISIBLE
            }
        }
    }

    private fun handleError(messageCode: String, data: List<Product>?){
        binding.apply {
            pbLoading.visibility = View.GONE
            if(data.isNullOrEmpty()){
                grpSearchResult.visibility = View.GONE
                grpEmptyList.visibility = View.VISIBLE
                if(messageCode == Constants.NO_INTERNET) {
                    tvEmptyList.setText(R.string.no_internet)
                    ivEmptyList.setImageResource(R.drawable.ic_no_signal)
                    btnRetry.visibility = View.VISIBLE
                }else{
                    tvEmptyList.setText(R.string.unknown_error)
                    ivEmptyList.setImageResource(R.drawable.ic_warning)
                    btnRetry.visibility = View.GONE
                }
            }else{
                grpSearchResult.visibility = View.VISIBLE
                llLoadingMoreError.visibility = View.VISIBLE
                btnRetry.visibility = View.GONE
                if(messageCode == Constants.NO_INTERNET) {
                    tvLoadingMoreError.setText(R.string.no_internet)
                }else{
                    tvLoadingMoreError.setText(R.string.unknown_error)
                }
            }
        }
    }
}