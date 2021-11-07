package com.example.appadoskotlin2.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.appadoskotlin2.R
import com.example.appadoskotlin2.data.RoomEntity
import com.example.appadoskotlin2.data.RoomResponse
import com.example.appadoskotlin2.ui.publish.PublishFragment

class AdapterPublish (val listener: PublishFragment,val services: ArrayList<RoomResponse>): RecyclerView.Adapter<AdapterPublish.PublishHolder>() {

    //TODO()

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PublishHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list, parent, false)
        return PublishHolder(itemView)
    }

    override fun getItemCount(): Int {
        return services.size
    }
    class PublishHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: RoomResponse, listener: (RoomResponse) -> Unit) = with(itemView) {

            if(rootView !== null) {
                rootView.findViewById<TextView>(R.id.tvStatusService).text = item.type
                setOnClickListener { listener(item)}
            }else{
                Log.d("error", "ha intentado levantar el diolog antes que el fragment")
            }
        }
    }


    override fun onBindViewHolder(holder: PublishHolder, position: Int) {
        holder.bind(services[position], listener)
    }
}