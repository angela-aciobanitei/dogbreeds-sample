package com.ang.acb.dogbreeds.details

import android.content.Context
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.ang.acb.dogbreeds.FakeBreedsRepository
import com.ang.acb.dogbreeds.R
import com.ang.acb.dogbreeds.domain.Breed
import com.ang.acb.dogbreeds.domain.BreedImage
import com.ang.acb.dogbreeds.domain.BreedsGateway
import com.ang.acb.dogbreeds.domain.SubBreed
import com.ang.acb.dogbreeds.utils.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

/**
 * Integration test for the breed images screen.
 */
@RunWith(AndroidJUnit4::class)
@MediumTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class BreedImagesFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var repository: BreedsGateway

    private val testBreed = Breed("corgi", listOf(SubBreed("cardigan")))

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun displayDogImages_whenRepoHasNoData_messageIsShown() {
        // Given no breed images in repo
        // When fragment is launched
        launchFragment()

        // Then a message is shown
        val message = getApplicationContext<Context>().getString(
            R.string.get_breed_images_empty_results_message
        )
        onView(withText(message)).check(matches(isDisplayed()))
    }

    @Test
    fun displayDogImages_whenRepoHasData_dataIsShown() {
        // Given 6 breed images in the repo
        val testImages = (0 until 6).map {
            BreedImage(
                breed = testBreed.name,
                url = "https://images.dog.ceo/breeds/${testBreed.name}/$it.jpg"
            )
        }
        runBlocking {
            (repository as FakeBreedsRepository).addImages(testImages)
        }

        // When fragment is launched
        launchFragment()

        // Then the list of dog breeds images are displayed
        onView(withId(R.id.rvBreedImages)).check(matches(hasChildCount(6)))
    }

    @Test
    fun loadDogImages_onError_messageIsShown() {
        // Given a repository that return errors
        (repository as FakeBreedsRepository).setReturnError(true)

        // When fragment is launched
        launchFragment()

        // Then an error message is shown
        val message = getApplicationContext<Context>().getString(
            R.string.get_breed_images_error_message
        )
        onView(withText(message)).check(matches(isDisplayed()))
    }

    private fun launchFragment() {
        val bundle = BreedImagesFragmentArgs(testBreed.name).toBundle()
        launchFragmentInHiltContainer<BreedImagesFragment>(bundle)
    }
}
