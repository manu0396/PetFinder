package com.example.appadoskotlin2.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.appadoskotlin2.R
import com.example.appadoskotlin2.data.RoomResponse

import com.example.appadoskotlin2.ui.home.HomeFragment

class AdapterHome(private val services: ArrayList<RoomResponse>, val listener: HomeFragment) : RecyclerView.Adapter<AdapterHome.HomeHolder>() {


    //TODO(No reconoce el método inflate del ViewGroup)

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list, parent, false)
        return HomeHolder(itemView)
    }
    override fun onBindViewHolder(holder: HomeHolder, position: Int) = holder.bind(services[position], listener)

    override fun getItemCount(): Int {
       return services.size
    }
    class HomeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: RoomResponse, listener: (RoomResponse) -> Unit) = with(itemView) {
            rootView.findViewById<TextView>(R.id.tvStatusService).text = item.toString()
            setOnClickListener { listener(item) }
        }
    }
}