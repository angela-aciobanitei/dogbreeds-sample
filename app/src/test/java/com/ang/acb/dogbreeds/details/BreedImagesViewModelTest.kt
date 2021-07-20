package com.ang.acb.dogbreeds.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.ang.acb.dogbreeds.FakeBreedsRepository
import com.ang.acb.dogbreeds.R
import com.ang.acb.dogbreeds.TestCoroutineRule
import com.ang.acb.dogbreeds.domain.Breed
import com.ang.acb.dogbreeds.domain.BreedImage
import com.ang.acb.dogbreeds.domain.GetBreedImagesUseCase
import com.ang.acb.dogbreeds.domain.SubBreed
import com.ang.acb.dogbreeds.utils.getOrAwaitValue
import com.ang.acb.dogbreeds.utils.observeForTesting
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class BreedImagesViewModelTest {

    // Subject under test
    private lateinit var viewModel: BreedImagesViewModel

    // Use a fake repository to be injected into the use case
    private lateinit var fakeRepository: FakeBreedsRepository

    private val testBreed = Breed("corgi", listOf(SubBreed("cardigan")))

    // Set the main coroutines dispatcher for unit testing
    @get:Rule
    var mainCoroutineRule = TestCoroutineRule()

    // Executes each task synchronously using Architecture Components
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel() {
        fakeRepository = FakeBreedsRepository()

        val bundle = mutableMapOf("breedName" to testBreed.name)
        viewModel = BreedImagesViewModel(
            SavedStateHandle(bundle.toMap()),
            GetBreedImagesUseCase(fakeRepository)
        )
    }

    @Test
    fun loadBreedImages_onSuccess_checkEmptyResults() {
        // Given no breed images in the repo
        // When retrieving the breed images list
        viewModel.getImages(testBreed.name)

        // Observe the breed images list to keep LiveData emitting
        viewModel.images.observeForTesting {
            // Then view model breed images list is empty too
            assertThat(viewModel.images.getOrAwaitValue().isEmpty(), `is`(true))

            // And the snackbar text is updated
            assertThat(
                viewModel.message.getOrAwaitValue().getContentIfNotHandled(),
                `is`(R.string.get_breed_images_empty_results_message)
            )
        }
    }

    @Test
    fun loadBreedImages_onSuccess_dataIsLoaded() {
        // Given 10 breed images in the repo
        val testBreedImagesList = (0 until 10).map {
            BreedImage(
                breed = testBreed.name,
                url = "https://images.dog.ceo/breeds/${testBreed.name}/$it.jpg"
            )
        }
        runBlocking {
            fakeRepository.addImages(testBreedImagesList)
        }

        // When retrieving the breed images list
        viewModel.getImages(testBreed.name)

        // Observe the breed images list to keep LiveData emitting
        viewModel.images.observeForTesting {
            // Then
            assertThat(viewModel.images.getOrAwaitValue().size, `is`(10))
        }
    }

    @Test
    fun loadBreedImages_onError_snackbarTextUpdated() {
        // Given a repository that return errors
        fakeRepository.setReturnError(true)

        // When retrieving the breed images list
        viewModel.getImages(testBreed.name)

        // Observe the breed images list to keep LiveData emitting
        viewModel.images.observeForTesting {
            // Then the snackbar text is updated
            assertThat(
                viewModel.message.getOrAwaitValue().getContentIfNotHandled(),
                `is`(R.string.get_breed_images_error_message)
            )
        }
    }
}
