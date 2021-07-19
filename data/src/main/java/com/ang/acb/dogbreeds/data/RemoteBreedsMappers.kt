package com.ang.acb.dogbreeds.data

import com.ang.acb.dogbreeds.domain.Breed
import com.ang.acb.dogbreeds.domain.BreedImage
import com.ang.acb.dogbreeds.domain.SubBreed

fun List<String>?.toBreedImages() = this?.map {
    BreedImage(url = it)
} ?: emptyList()

fun Map<String, List<String>>?.toBreeds() = this?.map { entry ->
    Breed(
        name = entry.key,
        subBreeds = entry.value.map { SubBreed(it) }
    )
} ?: emptyList()
