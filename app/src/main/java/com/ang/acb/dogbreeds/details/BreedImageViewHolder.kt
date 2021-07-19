package com.ang.acb.dogbreeds.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ang.acb.dogbreeds.R
import com.ang.acb.dogbreeds.databinding.ItemBreedImageBinding
import com.ang.acb.dogbreeds.domain.BreedImage
import com.bumptech.glide.Glide

class BreedImageViewHolder(
    private val binding: ItemBreedImageBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(image: BreedImage) {
        Glide.with(binding.root.context)
            .load(image.url)
            .centerCrop()
            // Display a placeholder until the image is loaded and processed.
            .placeholder(R.drawable.loading_anim)
            // Provide an error placeholder when Glide is unable to load the
            // image. This will be shown for the non-existing-url.
            .error(R.color.midnight50)
            // Use fallback image resource when the url can be null.
            .fallback(R.color.midnight50)
            .into(binding.ivBreed)

    }

    companion object {
        fun create(parent: ViewGroup): BreedImageViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemBreedImageBinding.inflate(inflater, parent, false)

            return BreedImageViewHolder(binding)
        }
    }
}
