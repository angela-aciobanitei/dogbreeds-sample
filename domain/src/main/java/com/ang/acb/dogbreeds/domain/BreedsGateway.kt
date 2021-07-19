package com.ang.acb.dogbreeds.domain

interface BreedsGateway {
    suspend fun getBreedsList(): List<Breed>
    suspend fun getBreedImages(breedName: String, imagesCount: Int): List<BreedImage>
}
