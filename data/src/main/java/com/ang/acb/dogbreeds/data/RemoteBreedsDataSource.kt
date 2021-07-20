package com.ang.acb.dogbreeds.data

import com.ang.acb.dogbreeds.domain.Breed
import com.ang.acb.dogbreeds.domain.BreedImage
import javax.inject.Inject

class RemoteBreedsDataSource @Inject constructor(
    private val dogApiService: DogApiService,
) {
    suspend fun getBreedsList(): List<Breed> {
        return dogApiService.getAllBreeds().message.toBreeds()
    }

    suspend fun getBreedImages(breedName: String, imagesCount: Int): List<BreedImage> {
        return dogApiService.getBreedImages(breedName, imagesCount).message.toBreedImages(breedName)
    }
}
