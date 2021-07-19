package com.ang.acb.dogbreeds.data

import com.ang.acb.dogbreeds.data.apiresponse.NetworkBreeds
import com.ang.acb.dogbreeds.data.apiresponse.NetworkImage
import com.ang.acb.dogbreeds.domain.Breed
import com.ang.acb.dogbreeds.domain.BreedImage
import com.ang.acb.dogbreeds.domain.SubBreed

fun NetworkImage.toBreedImage() = BreedImage(url)

fun List<NetworkImage>?.toBreedImages() = this?.map { it.toBreedImage() } ?: emptyList()

fun NetworkBreeds?.toBreeds() = this?.breedsMap?.map { entry ->
    Breed(
        name = entry.key,
        subBreeds = entry.value.map { SubBreed(it) }
    )
} ?: emptyList()
