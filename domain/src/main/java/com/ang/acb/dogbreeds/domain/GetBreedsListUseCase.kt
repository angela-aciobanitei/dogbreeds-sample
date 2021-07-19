package com.ang.acb.dogbreeds.domain

class GetBreedsListUseCase(private val breedsGateway: BreedsGateway) {
    suspend fun execute(): List<Breed> {
        return breedsGateway.getBreedsList()
    }
}
