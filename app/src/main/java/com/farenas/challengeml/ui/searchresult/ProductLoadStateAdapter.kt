package com.farenas.challengeml.ui.searchresult

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.farenas.challengeml.R
import com.farenas.challengeml.databinding.FooterLoadStateSearchResultBinding
import com.farenas.challengeml.utils.Constants
import javax.inject.Inject

class ProductLoadStateAdapter @Inject constructor()
    : LoadStateAdapter<ProductLoadStateAdapter.LoadStateViewHolder>() {

    private var listener: (() -> Unit)? = null

    fun setOnClickListener(listener: () -> Unit) {
        this.listener = listener
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {

        holder.binding.apply {
            btnRetry.isVisible = loadState !is LoadState.Loading
            tvMessage.isVisible = loadState !is LoadState.Loading
            pbLoading.isVisible = loadState is LoadState.Loading

            if (loadState is LoadState.Error){

                holder.binding.tvMessage.text = if (loadState.error.message == Constants.NO_INTERNET){
                    holder.itemView.context.getString(R.string.no_internet)
                }else{
                    holder.itemView.context.getString(R.string.unknown_error)
                }
            }

            holder.binding.btnRetry.setOnClickListener {
                listener?.invoke()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder =
            LoadStateViewHolder(
                FooterLoadStateSearchResultBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false)
            )

    class LoadStateViewHolder(val binding: FooterLoadStateSearchResultBinding) :
            RecyclerView.ViewHolder(binding.root)
}