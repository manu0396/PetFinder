package com.example.mvvm_retrofit_imagesearchapp.adapter.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.mvvm_retrofit_imagesearchapp.R
import com.example.mvvm_retrofit_imagesearchapp.data.UnsplashPhoto
import com.example.mvvm_retrofit_imagesearchapp.databinding.ItemUnsplashPhotoBinding

class AdapterDetail(private val listener: OnItemClickListenerGallery):
PagingDataAdapter<UnsplashPhoto,AdapterDetail.PhotoGalleryViewHolder>(COMPARATOR){

   inner class PhotoGalleryViewHolder(private val binding: ItemUnsplashPhotoBinding):
   RecyclerView.ViewHolder(binding.root){

       init {
           binding.root.setOnClickListener{
               val position = bindingAdapterPosition
               if(position != RecyclerView.NO_POSITION){
                   val item = getItem(position)
                   if(item != null)
                       listener.onItemClick(item)
               }
           }
       }

       fun bind (photo: UnsplashPhoto){
           binding.apply {
               textViewUserName.text = photo.user.username
               Glide.with(itemView)
                   .load(photo.urls.regular)
                   .centerCrop()
                   .transition(DrawableTransitionOptions.withCrossFade())
                   .error(R.drawable.ic_error)
                   .into(imageView)
           }
       }
    }

    override fun onBindViewHolder(holder: PhotoGalleryViewHolder, position: Int) {
       val currentItem = getItem(position)
        if(currentItem != null){
            holder.bind(currentItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoGalleryViewHolder {
        val binding = ItemUnsplashPhotoBinding.
        inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoGalleryViewHolder(binding)
    }
    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<UnsplashPhoto>() {
            override fun areItemsTheSame(oldItem: UnsplashPhoto, newItem: UnsplashPhoto): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: UnsplashPhoto,
                newItem: UnsplashPhoto
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    interface OnItemClickListenerGallery{
        fun onItemClick(photo: UnsplashPhoto)
    }
}



