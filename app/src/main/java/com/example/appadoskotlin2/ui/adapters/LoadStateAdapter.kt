package com.example.appadoskotlin2.ui.adapters

import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.appadoskotlin2.databinding.ItemServicesBinding
import com.example.appadoskotlin2.databinding.ServiceLoadStateFooterBinding

class LoadStateAdapter(private val retry : () -> Unit):
LoadStateAdapter<com.example.appadoskotlin2.ui.adapters.LoadStateAdapter.LoadStateViewHolder>(){
    override fun onBindViewHolder(
        holder: com.example.appadoskotlin2.ui.adapters.LoadStateAdapter.LoadStateViewHolder,
        loadState: LoadState,
    ) {
        TODO("Not yet implemented")
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState,
    ): com.example.appadoskotlin2.ui.adapters.LoadStateAdapter.LoadStateViewHolder {
        TODO("Not yet implemented")
    }
    inner class LoadStateViewHolder(private val binding: ServiceLoadStateFooterBinding):
            RecyclerView.ViewHolder(binding.root){

                init {
                    binding.btnRetry.setOnClickListener {
                        retry.invoke()
                    }
                }
        fun bind (loadState: LoadState){
            binding.apply {
                progressBar.isVisible = loadState is LoadState.Loading
                btnRetry.isVisible = loadState is LoadState.Loading
                tvServiceFooter.isVisible = loadState is LoadState.Loading
            }
        }
            }
}