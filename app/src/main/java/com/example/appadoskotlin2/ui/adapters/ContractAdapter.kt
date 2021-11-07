package com.example.appadoskotlin2.ui.adapters

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.appadoskotlin2.data.ServiceItemResponse
import com.example.appadoskotlin2.data.ServiceResponse
import com.example.appadoskotlin2.databinding.ItemServicesBinding

class ContractAdapter(private val listener: OnItemClickListener) :
    PagingDataAdapter<ServiceItemResponse, ContractAdapter.ContractHolder>(COMPARATOR) {
    override fun onBindViewHolder(holder: ContractAdapter.ContractHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ContractAdapter.ContractHolder {
        TODO("Not yet implemented")
    }
    inner class ContractHolder(private val binding:ItemServicesBinding):
            RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if(position != RecyclerView.NO_POSITION){
                    val item = getItem(position)
                    if(item != null){
                        listener.OnItemClick(item)
                    }
                }
            }
        }
        fun bind(service: ServiceItemResponse){
            binding.apply {

            }
        }
    }
    interface OnItemClickListener{
        fun OnItemClick(service: ServiceItemResponse)
    }

    companion object{
        private val COMPARATOR = object : DiffUtil.ItemCallback<ServiceItemResponse>(){
            override fun areItemsTheSame(
                oldItem: ServiceItemResponse,
                newItem: ServiceItemResponse,
            ): Boolean {
                return newItem.idService==oldItem.idService
            }

            override fun areContentsTheSame(
                oldItem: ServiceItemResponse,
                newItem: ServiceItemResponse,
            ): Boolean {
               return oldItem==newItem
            }

        }
    }
}