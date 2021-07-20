package com.ang.acb.dogbreeds.domain

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class GetBreedImagesUseCaseTest {
    // Subject under test
    private lateinit var useCase: GetBreedImagesUseCase

    // Use a fake repository to be injected into the use case
    private lateinit var fakeRepository: FakeBreedsRepository

    // Sets the main coroutines dispatcher for unit testing
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        fakeRepository = FakeBreedsRepository()
        useCase = GetBreedImagesUseCase(fakeRepository)
    }

    @Test
    fun getBreedImages_resultIsNotNull() {
        mainCoroutineRule.runBlockingTest {
            // Given 2 breed images that are saved
            val testBreedName = "akita"
            val testImages = listOf(
                BreedImage(breed = testBreedName, url = "url1"),
                BreedImage(breed = testBreedName, url = "url2"),
            )
            fakeRepository.addImages(testImages)

            // When loading the requested breed images
            val loaded = useCase.execute(testBreedName, testImages.size)

            // Then the loaded data is not null
            assertThat(loaded, notNullValue())
        }
    }

    @Test
    fun getBreedImages_resultIsNotEmpty() {
        mainCoroutineRule.runBlockingTest {
            // Given 3 breed images that are saved
            val testBreedName = "corgi"
            val testImages = listOf(
                BreedImage(breed = testBreedName, url = "url1"),
                BreedImage(breed = testBreedName, url = "url2"),
                BreedImage(breed = testBreedName, url = "url3"),
            )
            fakeRepository.addImages(testImages)

            // When loading the requested breed images
            val loaded = useCase.execute(testBreedName, testImages.size)

            // Then the loaded data is not empty
            assertThat(loaded.isNotEmpty(), `is`(true))
        }
    }

    @Test
    fun getBreedImages_verifyResultSize() {
        mainCoroutineRule.runBlockingTest {
            // Given 4 breed images that are saved
            val testBreedName = "dingo"
            val testImages = listOf(
                BreedImage(breed = testBreedName, url = "url1"),
                BreedImage(breed = testBreedName, url = "url2"),
                BreedImage(breed = testBreedName, url = "url3"),
                BreedImage(breed = testBreedName, url = "url4"),
            )
            fakeRepository.addImages(testImages)

            // When loading the requested breed images
            val loaded = useCase.execute(testBreedName, testImages.size)

            // Then the loaded data size is as expected
            assertThat(loaded.size, `is`(testImages.size))
        }
    }
}
