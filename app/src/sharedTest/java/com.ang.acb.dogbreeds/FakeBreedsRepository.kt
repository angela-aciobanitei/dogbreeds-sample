package com.ang.acb.dogbreeds

import com.ang.acb.dogbreeds.domain.Breed
import com.ang.acb.dogbreeds.domain.BreedImage
import com.ang.acb.dogbreeds.domain.BreedsGateway
import javax.inject.Inject

class FakeBreedsRepository @Inject constructor() : BreedsGateway {

    private var breedsList = mutableListOf<Breed>()
    private var breedImages = mutableListOf<BreedImage>()
    private var shouldReturnError = false

    override suspend fun getBreedsList(): List<Breed> {
        return if (shouldReturnError) {
            throw Exception("Test exception")
        } else {
            breedsList
        }
    }

    override suspend fun getBreedImages(breedName: String, imagesCount: Int): List<BreedImage> {
        return if (shouldReturnError) {
            throw Exception("Test exception")
        } else {
            breedImages
        }
    }

    fun addBreeds(breeds: List<Breed>) {
        breedsList.addAll(breeds)
    }

    fun addImages(images: List<BreedImage>) {
        breedImages.addAll(images)
    }

    fun setReturnError(value: Boolean) {
        shouldReturnError = value
    }
}
