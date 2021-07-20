package com.ang.acb.dogbreeds.data.utils

import androidx.test.espresso.IdlingResource

/**
 * Contains a static reference to [IdlingResource], only available in the 'mock' build type.
 *
 * See: https://github.com/android/architecture-samples/tree/dev-hilt
 */
object EspressoIdlingResource {

    private const val RESOURCE = "GLOBAL"

    @JvmField
    val countingIdlingResource = SimpleCountingIdlingResource(RESOURCE)

    fun increment() {
        countingIdlingResource.increment()
    }

    fun decrement() {
        if (!countingIdlingResource.isIdleNow) {
            countingIdlingResource.decrement()
        }
    }
}

inline fun <T> wrapEspressoIdlingResource(function: () -> T): T {
    // Espresso does not work well with coroutines yet. See
    // https://github.com/Kotlin/kotlinx.coroutines/issues/982
    // Set app as busy
    EspressoIdlingResource.increment()
    return try {
        function()
    } finally {
        // Set app as idle
        EspressoIdlingResource.decrement()
    }
}

