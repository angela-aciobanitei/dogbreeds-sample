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

    suspend fun getBreedImages(breedName: String): List<BreedImage> {
        return dogApiService.getBreedImages(breedName).message.toBreedImages()
    }
}
