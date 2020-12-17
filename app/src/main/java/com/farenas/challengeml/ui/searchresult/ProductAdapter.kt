package com.farenas.challengeml.ui.searchresult

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.farenas.challengeml.R
import com.farenas.challengeml.data.model.Product
import com.farenas.challengeml.databinding.ItemProductBinding
import com.farenas.challengeml.utils.TextUtils
import javax.inject.Inject

class ProductAdapter @Inject constructor()
    : PagingDataAdapter<Product, ProductAdapter.ViewHolder>(ProductComparator) {

    private var listener: ((Product) -> Unit)? = null

    fun setOnClickListener(listener: (Product) -> Unit) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ItemProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }

    class ViewHolder(private val itemBinding: ItemProductBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(product: Product?, listener: ((item: Product) -> Unit)?) {
            itemBinding.apply {
                tvTitle.text = product?.title
                tvPrice.text = product?.let {
                        p -> TextUtils.getCurrencyFormat(p.price, p.currencyId)
                }?: ""
                tvLocation.text = product?.address?.stateName
                ivProduct.load(product?.thumbnail){
                    size(250)
                    placeholder(R.drawable.ic_image_placeholder)
                    error(R.drawable.ic_image_placeholder)
                }
                ivProduct.contentDescription = product?.title

                product?.let { p -> root.setOnClickListener{ listener?.invoke(p) } }

            }
        }
    }

    object ProductComparator : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean =
                oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean =
                oldItem == newItem
    }
}