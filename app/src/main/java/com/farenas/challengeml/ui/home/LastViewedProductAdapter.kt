package com.farenas.challengeml.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.farenas.challengeml.R
import com.farenas.challengeml.data.model.Product
import com.farenas.challengeml.databinding.ItemLastViewedProductBinding
import com.farenas.challengeml.utils.TextUtils
import javax.inject.Inject

class LastViewedProductAdapter @Inject constructor()
    : ListAdapter<Product, LastViewedProductAdapter.ViewHolder>(ProductComparator) {

    private var listener: ((item: Product) -> Unit)? = null

    fun setOnClickListener(listener: (item: Product) -> Unit) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ItemLastViewedProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }

    class ViewHolder(private val itemBinding: ItemLastViewedProductBinding)
        : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(result: Product, listener: ((item: Product) -> Unit)?) {
            itemBinding.apply {
                tvTitle.text = result.title
                tvPrice.text = TextUtils.getCurrencyFormat(result.price, result.currencyId)
                tvLocation.text = result.address?.stateName
                ivProduct.load(result.thumbnail){
                    placeholder(R.drawable.ic_image_placeholder)
                }
                ivProduct.contentDescription = result.title

                root.setOnClickListener{ listener?.invoke(result) }
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