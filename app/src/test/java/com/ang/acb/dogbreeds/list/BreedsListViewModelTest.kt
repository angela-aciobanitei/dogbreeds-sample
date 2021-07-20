package com.ang.acb.dogbreeds.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ang.acb.dogbreeds.FakeBreedsRepository
import com.ang.acb.dogbreeds.R
import com.ang.acb.dogbreeds.TestCoroutineRule
import com.ang.acb.dogbreeds.domain.Breed
import com.ang.acb.dogbreeds.domain.GetBreedsListUseCase
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
class BreedsListViewModelTest {

    // Subject under test
    private lateinit var viewModel: BreedsListViewModel

    // Use a fake repository to be injected into the use case
    private lateinit var fakeRepository: FakeBreedsRepository

    // Set the main coroutines dispatcher for unit testing
    @get:Rule
    var mainCoroutineRule = TestCoroutineRule()

    // Executes each task synchronously using Architecture Components
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel() {
        fakeRepository = FakeBreedsRepository()

        viewModel = BreedsListViewModel(GetBreedsListUseCase(fakeRepository))
    }

    @Test
    fun loadAllBreeds_onSuccess_checkEmptyResults() {
        // Given no breeds in the repo
        // When retrieving the breeds list
        viewModel.loadAllBreeds()

        viewModel.breeds.observeForTesting {
            // Then the view model breeds list is empty too
            assertThat(viewModel.breeds.getOrAwaitValue().isEmpty(), `is`(true))

            // And the snackbar text is updated
            assertThat(
                viewModel.message.getOrAwaitValue().getContentIfNotHandled(),
                `is`(R.string.get_breeds_list_empty_results_message)
            )
        }
    }

    @Test
    fun loadAllBreeds_onSuccess_checkLoadingTogglesAndData() {
        // Given 4 breeds in the repo
        val testBreedsList = listOf(
            Breed("cattledog", listOf(SubBreed("australian"))),
            Breed("chow", emptyList()),
            Breed("collie", listOf(SubBreed("border"))),
            Breed("corgi", listOf(SubBreed("cardigan"))),
        )
        runBlocking {
            fakeRepository.addBreeds(testBreedsList)
        }
        // Pause dispatcher so we can verify initial values
        mainCoroutineRule.pauseDispatcher()

        // When retrieving the breeds list
        viewModel.loadAllBreeds()

        viewModel.breeds.observeForTesting {
            // Then progress indicator is shown
            assertThat(viewModel.loading.getOrAwaitValue(), `is`(true))

            // Execute pending coroutines actions
            mainCoroutineRule.resumeDispatcher()

            // Then progress indicator is hidden
            assertThat(viewModel.loading.getOrAwaitValue(), `is`(false))

            // And data correctly loaded
            assertThat(viewModel.breeds.getOrAwaitValue().size, `is`(4))
        }
    }

    @Test
    fun loadAllBreeds_onError_loadingIsHidden() {
        // Given a repository that return errors
        fakeRepository.setReturnError(true)

        // When retrieving the breeds list
        viewModel.loadAllBreeds()

        // Then the progress indicator is hidden
        viewModel.breeds.observeForTesting {
            assertThat(viewModel.loading.getOrAwaitValue(), `is`(false))
        }
    }

    @Test
    fun loadAllBreeds_onError_snackbarTextUpdated() {
        // Given a repository that return errors
        fakeRepository.setReturnError(true)

        // When retrieving the breeds list
        viewModel.loadAllBreeds()

        // Then the snackbar text is updated
        viewModel.breeds.observeForTesting {
            assertThat(
                viewModel.message.getOrAwaitValue().getContentIfNotHandled(),
                `is`(R.string.get_breeds_list_error_message)
            )
        }
    }

    @Test
    fun clickOnBreedItem_setsNavigationEvent() {
        val breedName = "corgi"
        viewModel.onBreedClick(breedName)

        assertThat(
            viewModel.navigation.getOrAwaitValue().getContentIfNotHandled(),
            `is`(BreedsListViewModel.Navigation.ToBreedImages(breedName))
        )
    }
}
