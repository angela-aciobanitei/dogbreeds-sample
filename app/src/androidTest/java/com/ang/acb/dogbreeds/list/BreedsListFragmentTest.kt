package com.ang.acb.dogbreeds.list

import android.content.Context
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.ang.acb.dogbreeds.FakeBreedsRepository
import com.ang.acb.dogbreeds.MainActivity
import com.ang.acb.dogbreeds.R
import com.ang.acb.dogbreeds.domain.Breed
import com.ang.acb.dogbreeds.domain.BreedsGateway
import com.ang.acb.dogbreeds.domain.SubBreed
import com.ang.acb.dogbreeds.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

/**
 * Integration test for the breeds list screen.
 */
@RunWith(AndroidJUnit4::class)
@MediumTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class BreedsListFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var repository: BreedsGateway

    @Before
    fun init() {
        // Populate @Inject fields in test class
        hiltRule.inject()
    }

    @Test
    fun displayDogBreedsList_whenRepoHasNoData_loadingStops() {
        // Given no breeds in repo
        // When activity is launched
        launchActivity()

        // Then the progress bar is hidden
        onView(withId(R.id.progressBar)).check(matches(not(isDisplayed())))
    }

    @Test
    fun displayDogBreedsList_whenRepoHasNoData_messageShown() {
        // Given no breeds in repo
        // When  activity is launched
        launchActivity()

        // Then a message is shown
        val message =
            getApplicationContext<Context>().getString(R.string.get_breeds_list_empty_results_message)
        onView(withText(message)).check(matches(isDisplayed()))
    }

    @Test
    fun displayDogBreedsList_whenRepoHasData() {
        // Given 4 breeds that are saved in the repo
        val testBreedsList = listOf(
            Breed("cattledog", listOf(SubBreed("australian"))),
            Breed("chow", emptyList()),
            Breed("collie", listOf(SubBreed("border"))),
            Breed("corgi", listOf(SubBreed("cardigan"))),
        )
        runBlocking {
            (repository as FakeBreedsRepository).addBreeds(testBreedsList)
        }
        // When activity is launched
        launchActivity()

        // Then the list of dog breeds displayed matches
        onView(withText(testBreedsList[0].name.capitalize())).check(matches(isDisplayed()))
        onView(withText(testBreedsList[1].name.capitalize())).check(matches(isDisplayed()))
        onView(withText(testBreedsList[2].name.capitalize())).check(matches(isDisplayed()))
        onView(withText(testBreedsList[3].name.capitalize())).check(matches(isDisplayed()))
    }

    @Test
    fun loadDogBreedsList_error_loadingStops() {
        // Given 2 breeds that are saved in the repo, make the repository return errors
        val testBreedsList = listOf(
            Breed("collie", listOf(SubBreed("border"))),
            Breed("corgi", listOf(SubBreed("cardigan"))),
        )
        runBlocking {
            (repository as FakeBreedsRepository).addBreeds(testBreedsList)
            (repository as FakeBreedsRepository).setReturnError(true)

        }
        // When activity is launched
        launchActivity()

        // Then the progress bar is hidden
        onView(withId(R.id.progressBar)).check(matches(not(isDisplayed())))
    }

    @Test
    fun loadDogBreedsList_error_snackBarErrorMessageIsShown() {
        // Given 2 breeds that are saved in the repo, make the repository return errors
        val testBreedsList = listOf(
            Breed("collie", listOf(SubBreed("border"))),
            Breed("corgi", listOf(SubBreed("cardigan"))),
        )
        runBlocking {
            (repository as FakeBreedsRepository).addBreeds(testBreedsList)
            (repository as FakeBreedsRepository).setReturnError(true)

        }
        // When activity is launched
        launchActivity()

        // Then an error message is shown
        val message =
            getApplicationContext<Context>().getString(R.string.get_breeds_list_error_message)
        onView(withText(message)).check(matches(isDisplayed()))
    }

    @Test
    fun clickOnBreedItem_navigatesToBreedImagesFragment() {
        // Given 2 breeds that are saved in the repo
        val testBreedsList = listOf(
            Breed("collie", listOf(SubBreed("border"))),
            Breed("corgi", listOf(SubBreed("cardigan"))),
        )
        runBlocking {
            (repository as FakeBreedsRepository).addBreeds(testBreedsList)
        }
        // When on the home screen, clicking on the first item
        val navController = TestNavHostController(getApplicationContext())
        launchBreedsListFragment(navController)

        onView(withText(testBreedsList[0].name.capitalize())).perform(ViewActions.click())

        // Then we navigate to the breed images screen
        assertThat(navController.currentDestination?.id, `is`(R.id.breedImagesFragment))
    }

    private fun launchActivity(): ActivityScenario<MainActivity>? {
        val activityScenario = launch(MainActivity::class.java)
        activityScenario.onActivity { activity ->
            // Disable animations in RecyclerView
            (activity.findViewById(R.id.rvBreeds) as RecyclerView).itemAnimator = null
        }
        return activityScenario
    }

    private fun launchBreedsListFragment(navController: TestNavHostController) {
        launchFragmentInHiltContainer<BreedsListFragment>(Bundle()) {
            navController.setGraph(R.navigation.main_navigation)
            navController.setCurrentDestination(R.id.breedsListFragment)
            Navigation.setViewNavController(requireView(), navController)
        }
    }
}
