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
class GetBreedsListUseCaseTest {
    // Subject under test
    private lateinit var useCase: GetBreedsListUseCase

    // Use a fake repository to be injected into the use case
    private lateinit var fakeRepository: FakeBreedsRepository

    // Sets the main coroutines dispatcher for unit testing
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        fakeRepository = FakeBreedsRepository()
        useCase = GetBreedsListUseCase(fakeRepository)
    }

    @Test
    fun getAllBreeds_resultIsNotNull() {
        mainCoroutineRule.runBlockingTest {
            // Given 2 breeds that are saved
            fakeRepository.addBreeds(
                listOf(
                    Breed("akita", emptyList()),
                    Breed("australian", emptyList()),
                )
            )

            // When loading all the breeds
            val loaded = useCase.execute()

            // Then the loaded data is not null
            assertThat(loaded, notNullValue())
        }
    }

    @Test
    fun getAllBreeds_resultIsNotEmpty() {
        mainCoroutineRule.runBlockingTest {
            // Given 3 breeds that are saved
            fakeRepository.addBreeds(
                listOf(
                    Breed("beagle", emptyList()),
                    Breed("bluetick", emptyList()),
                    Breed("borzoi", emptyList()),
                )
            )

            // When loading all the breeds
            val loaded = useCase.execute()

            // Then the loaded data is not empty
            assertThat(loaded.isNotEmpty(), `is`(true))
        }
    }

    @Test
    fun getAllBreeds_verifyResultSize() {
        mainCoroutineRule.runBlockingTest {
            // Given 4 breeds that are saved
            fakeRepository.addBreeds(
                listOf(
                    Breed("cattledog", emptyList()),
                    Breed("chow", emptyList()),
                    Breed("collie", emptyList()),
                    Breed("corgi", emptyList()),
                )
            )

            // When loading all the breeds
            val loaded = useCase.execute()

            // Then the loaded data size is as expected
            assertThat(loaded.size, `is`(4))
        }
    }
}
