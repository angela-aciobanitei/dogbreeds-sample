package com.ang.acb.dogbreeds.list

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.ang.acb.dogbreeds.domain.Breed

class BreedListAdapter(
    private val onItemClick: (breedName: String) -> Unit,
) : ListAdapter<Breed, BreedItemViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedItemViewHolder {
        return BreedItemViewHolder.create(parent, onItemClick)
    }

    override fun onBindViewHolder(holder: BreedItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Breed>() {
            override fun areItemsTheSame(oldItem: Breed, newItem: Breed): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Breed, newItem: Breed): Boolean {
                return oldItem == newItem
            }
        }
    }
}
