package com.ang.acb.dogbreeds.list

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.ang.acb.dogbreeds.FakeBreedsRepository
import com.ang.acb.dogbreeds.MainActivity
import com.ang.acb.dogbreeds.R
import com.ang.acb.dogbreeds.domain.Breed
import com.ang.acb.dogbreeds.domain.BreedsGateway
import com.ang.acb.dogbreeds.domain.SubBreed
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
        // When on start
        launchActivity()

        // Then the list of dog breeds displayed matches
        onView(withText(testBreedsList[0].name.capitalize())).check(matches(isDisplayed()))
        onView(withText(testBreedsList[1].name.capitalize())).check(matches(isDisplayed()))
        onView(withText(testBreedsList[2].name.capitalize())).check(matches(isDisplayed()))
        onView(withText(testBreedsList[3].name.capitalize())).check(matches(isDisplayed()))
    }

    private fun launchActivity(): ActivityScenario<MainActivity>? {
        val activityScenario = launch(MainActivity::class.java)
        activityScenario.onActivity { activity ->
            // Disable animations in RecyclerView
            (activity.findViewById(R.id.rvBreeds) as RecyclerView).itemAnimator = null
        }
        return activityScenario
    }
}
