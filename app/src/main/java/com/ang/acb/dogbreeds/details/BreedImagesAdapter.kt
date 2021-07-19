package com.ang.acb.dogbreeds.details

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.ang.acb.dogbreeds.domain.BreedImage

class BreedImagesAdapter : ListAdapter<BreedImage, BreedImageViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedImageViewHolder {
        return BreedImageViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: BreedImageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<BreedImage>() {
            override fun areItemsTheSame(oldItem: BreedImage, newItem: BreedImage): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(oldItem: BreedImage, newItem: BreedImage): Boolean {
                return oldItem == newItem
            }
        }
    }
}
