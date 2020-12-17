package com.farenas.challengeml.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.farenas.challengeml.data.model.Attribute
import com.farenas.challengeml.databinding.ItemAttributeBinding
import javax.inject.Inject

class AttributeAdapter @Inject constructor()
    : ListAdapter<Attribute, AttributeAdapter.ViewHolder>(AttibuteComparator){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ItemAttributeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val itemBinding: ItemAttributeBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(attribute: Attribute) {
            itemBinding.apply {
                tvName.text = attribute.name
                tvValue.text = attribute.value
            }
        }
    }

    object AttibuteComparator : DiffUtil.ItemCallback<Attribute>() {
        override fun areItemsTheSame(oldItem: Attribute, newItem: Attribute): Boolean =
                oldItem.productId == newItem.productId
                    && oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: Attribute, newItem: Attribute): Boolean =
                oldItem == newItem
    }
}