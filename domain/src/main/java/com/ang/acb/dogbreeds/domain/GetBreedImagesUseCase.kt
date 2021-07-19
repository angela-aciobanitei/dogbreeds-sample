package com.ang.acb.dogbreeds.domain

class GetBreedImagesUseCase(private val breedsGateway: BreedsGateway) {
    suspend fun execute(breedName: String): List<BreedImage> {
        return breedsGateway.getBreedImage(breedName)
    }
}
