package com.ang.acb.dogbreeds.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ang.acb.dogbreeds.databinding.ItemBreedBinding
import com.ang.acb.dogbreeds.domain.Breed

class BreedItemViewHolder(
    private val binding: ItemBreedBinding,
    val onItemClick: (breedName: String) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(breed: Breed) {
        binding.tvName.text = breed.name
        binding.contactRoot.setOnClickListener {
            onItemClick(breed.name)
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            onItemClick: (breedName: String) -> Unit,
        ): BreedItemViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemBreedBinding.inflate(inflater, parent, false)

            return BreedItemViewHolder(binding, onItemClick)
        }
    }
}
