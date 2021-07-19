package com.ang.acb.dogbreeds.domain

interface BreedsGateway {
    suspend fun getBreedsList(): List<Breed>
    suspend fun getBreedImage(breedName: String): List<BreedImage>
}
