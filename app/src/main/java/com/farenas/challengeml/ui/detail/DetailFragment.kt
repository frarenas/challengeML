package com.farenas.challengeml.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import coil.load
import com.farenas.challengeml.R
import com.farenas.challengeml.data.model.Product
import com.farenas.challengeml.data.repo.ProductRepository
import com.farenas.challengeml.databinding.FragmentDetailBinding
import com.farenas.challengeml.ui.base.BaseFragment
import com.farenas.challengeml.utils.Constants
import com.farenas.challengeml.utils.TextUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment(
    private val productRepository: ProductRepository
) : BaseFragment<FragmentDetailBinding>(R.layout.fragment_detail) {

    private lateinit var product: Product

    @Inject
    lateinit var attributeAdapter: AttributeAdapter

    private val viewModel: DetailViewModel by viewModels {
        DetailViewModelFactory(productRepository, this, requireArguments())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailBinding.bind(view)

        viewModel
        product = requireArguments().getParcelable(Constants.PARAM_PRODUCT)!!
        setView()
    }

    private fun setView(){
        binding.apply {
            ivProduct.load(product.thumbnail) {
                placeholder(R.drawable.ic_image_placeholder)
                error(R.drawable.ic_image_placeholder)
            }
            ivProduct.contentDescription = product.title

            tvTitle.text = product.title
            tvPrice.text = TextUtils.getCurrencyFormat(product.price, product.currencyId)
            product.address?.let {
                tvLocation.text = String.format(getString(R.string.place_format), it.cityName, it.stateName)
            }

            product.attributes?.let {
                rvAttributes.apply {
                    adapter = attributeAdapter
                    layoutManager = GridLayoutManager(requireActivity(), 2)
                }

                attributeAdapter.submitList(it)
            }
        }
    }
}