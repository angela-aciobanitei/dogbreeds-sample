package com.ang.acb.dogbreeds.data

import retrofit2.http.GET
import retrofit2.http.Path

interface DogApiService {
    @GET("breeds/list/all")
    suspend fun getAllBreeds(): BreedsListResponse

    @GET("breed/{breedName}/images/random/{imagesCount}")
    suspend fun getBreedImages(
        @Path("breedName") breedName: String,
        @Path("imagesCount") imagesCount: Int,
    ): BreedImagesResponse
}
