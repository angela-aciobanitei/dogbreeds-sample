package com.ang.acb.dogbreeds

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ang.acb.dogbreeds.data.utils.EspressoIdlingResource
import com.ang.acb.dogbreeds.domain.Breed
import com.ang.acb.dogbreeds.domain.BreedsGateway
import com.ang.acb.dogbreeds.domain.SubBreed
import com.ang.acb.dogbreeds.utils.DataBindingIdlingResource
import com.ang.acb.dogbreeds.utils.monitorActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.containsString
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class AppNavigationTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var repository: BreedsGateway

    // An Idling Resource that waits for data binding to have no pending bindings
    private val dataBindingIdlingResource = DataBindingIdlingResource()

    @Before
    fun init() {
        hiltRule.inject()
    }

    // Idling resources tell Espresso that the app is idle or busy. This is needed
    // when operations are not scheduled in the main Looper (for example, when
    // executed on a different thread).
    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().register(dataBindingIdlingResource)
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().unregister(dataBindingIdlingResource)
    }

    @Test
    fun homeScreen_toDetailsScreen_pressBackButton() {
        // Given 2 breeds that are saved in the repo
        val testBreedsList = listOf(
            Breed("collie", listOf(SubBreed("border"))),
            Breed("corgi", listOf(SubBreed("cardigan"))),
        )
        runBlocking {
            (repository as FakeBreedsRepository).addBreeds(testBreedsList)
        }

        // Start home screen
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        // Click on first item on the list
        onView(withId(R.id.rvBreeds)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.rvBreedImages))
            .check(ViewAssertions.matches(isDisplayed()))

        // Confirm that if we click back we end up back at the home screen
        pressBack()
        onView(withId(R.id.rvBreeds))
            .check(ViewAssertions.matches(isDisplayed()))

        // When using ActivityScenario.launch, always call close()
        activityScenario.close()
    }

    @Test
    fun homeScreen_toDetailsScreen_pressUpButton() {
        // Given 2 breeds that are saved in the repo
        val testBreedsList = listOf(
            Breed("collie", listOf(SubBreed("border"))),
            Breed("corgi", listOf(SubBreed("cardigan"))),
        )
        runBlocking {
            (repository as FakeBreedsRepository).addBreeds(testBreedsList)
        }

        // Start home screen
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        // Click on first item on the list
        onView(withId(R.id.rvBreeds)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.rvBreedImages))
            .check(ViewAssertions.matches(isDisplayed()))

        // Confirm that if we click up we end up back at the home screen
        onView(allOf(withContentDescription(containsString("Navigate up")), isClickable()))
            .perform(click())
        onView(withId(R.id.rvBreeds))
            .check(ViewAssertions.matches(isDisplayed()))

        // When using ActivityScenario.launch, always call close()
        activityScenario.close()
    }
}
