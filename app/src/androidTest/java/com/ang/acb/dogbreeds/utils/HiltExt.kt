package com.ang.acb.dogbreeds.utils

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import androidx.core.util.Preconditions
import androidx.fragment.app.Fragment
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import com.ang.acb.dogbreeds.HiltTestActivity

/**
 *
 * Hilt does not currently support FragmentScenario because there is no way to specify an
 * activity class, and Hilt requires a Hilt fragment to be contained in a Hilt activity.
 * One workaround for this is to launch a Hilt activity and then attach your fragment.
 *
 * The launchFragmentInContainer method from the androidx.fragment:fragment-testing library
 * is NOT possible to be used with Hilt right now as it uses a hardcoded Activity under the
 * hood (i.e. EmptyFragmentActivity) which is not annotated with @AndroidEntryPoint.
 *
 * As a workaround, use this function that is equivalent. It requires you to add
 * [HiltTestActivity] in the debug folder and include it in the debug AndroidManifest.xml file.
 *
 * See: https://developer.android.com/training/dependency-injection/hilt-testing#launchfragment
 */
inline fun <reified T : Fragment> launchFragmentInHiltContainer(
    fragmentArgs: Bundle? = null,
    crossinline action: Fragment.() -> Unit = {}
) {
    val startActivityIntent = Intent.makeMainActivity(
        ComponentName(
            ApplicationProvider.getApplicationContext(),
            HiltTestActivity::class.java
        )
    )

    ActivityScenario.launch<HiltTestActivity>(startActivityIntent).onActivity { activity ->
        val fragment: Fragment = activity.supportFragmentManager.fragmentFactory.instantiate(
            Preconditions.checkNotNull(T::class.java.classLoader),
            T::class.java.name
        )
        fragment.arguments = fragmentArgs
        activity.supportFragmentManager
            .beginTransaction()
            .add(android.R.id.content, fragment, "")
            .commitNow()

        fragment.action()
    }
}
