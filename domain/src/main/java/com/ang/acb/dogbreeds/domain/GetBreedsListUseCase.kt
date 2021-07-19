package com.ang.acb.dogbreeds.domain

import javax.inject.Inject

class GetBreedsListUseCase @Inject constructor(
    private val breedsGateway: BreedsGateway,
) {
    suspend fun execute(): List<Breed> {
        return breedsGateway.getBreedsList()
    }
}
