package com.ang.acb.dogbreeds.data

import com.ang.acb.dogbreeds.data.utils.wrapEspressoIdlingResource
import com.ang.acb.dogbreeds.domain.Breed
import com.ang.acb.dogbreeds.domain.BreedImage
import com.ang.acb.dogbreeds.domain.BreedsGateway
import javax.inject.Inject

class BreedsRepository @Inject constructor(
    private val dataSource: RemoteBreedsDataSource,
) : BreedsGateway {
    override suspend fun getBreedsList(): List<Breed> {
        // Set app as busy while this function executes.
        wrapEspressoIdlingResource {
            return dataSource.getBreedsList()
        }
    }

    override suspend fun getBreedImages(breedName: String, imagesCount: Int): List<BreedImage> {
        wrapEspressoIdlingResource {
            return dataSource.getBreedImages(breedName, imagesCount)
        }
    }
}
