package com.ang.acb.dogbreeds.data

import retrofit2.http.GET
import retrofit2.http.Path

interface DogApiService {
    @GET("breeds/list/all")
    suspend fun getAllBreeds(): BreedsListResponse

    @GET("breed/{breed}/images/random/{imageCount}")
    suspend fun getBreedImages(
        @Path("breed") breed: String,
        @Path("imageCount") imageCount: Int,

        ): BreedImagesResponse
}