package com.ang.acb.dogbreeds.domain

import javax.inject.Inject

class GetBreedImagesUseCase @Inject constructor(
    private val breedsGateway: BreedsGateway,
) {
    suspend fun execute(breedName: String): List<BreedImage> {
        return breedsGateway.getBreedImages(breedName)
    }
}
