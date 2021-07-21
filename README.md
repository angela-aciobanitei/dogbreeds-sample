# Popular Dog Breeds

A sample app that uses the [Dog API](https://dog.ceo/dog-api/documentation/) to display a list of dog breeds and corresponding (random) pictures.

## Core Libraries
*   [Hilt](https://dagger.dev/hilt/) for dependency injection
*   [Retrofit 2](https://github.com/square/retrofit) and [OkHttp](https://github.com/square/okhttp) for networking
*   [Gson](https://github.com/google/gson) for parsing JSON
*   [Navigation](https://developer.android.com/jetpack/compose/navigation) to navigate between screens
*   [Glide](https://github.com/bumptech/glide) for image loading

## App Architecture
The app uses clean architecture. For simplicity, it has 3 layers:
* the **domain layer** contains the business logic of the app. It holds the business models, the abstract definitions of the repositories, some mappers, and the use cases.
* the **data layer** provides definitions of the data sources. It holds the concrete implementations of the repositories and other data sources like networking via Retrofit in this case. 
* the **presentation layer** contains the UI-related code and makes use of the MVVM pattern in this particular case.

## Testing
* I added local unit tests to test the usecases
* I also added integration tests for view models and fragments
* in order to test in isolation, I  have created a fake repository that implements the same interface as the real repository. This way the two implementations can be swapped during testing. Hilt made this very easy: in order to replace a binding, I just had to replace the module that contains the binding with a test module that contains the binding that I wanted to use in the test (i.e. the fake repo).
* to handle coroutines testing I have used the TestCoroutineDispatcher provided by the [kotlinx-coroutines-test](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/index.html) library
* to test the REST API interface defined for Retrofit I made use of [MockWebServer](https://github.com/square/okhttp/tree/master/mockwebserver)
* for UI testing I used [Espresso](https://developer.android.com/training/testing/espresso)

## Problems I have encountered
* since Espresso doesn't play nicely with coroutines, data binding or Hilt, I had to add a lot of boilerplate code that was not written by me. I mostly used the Google samples as examples, and every copied class/file has links in the comments that point to the original code.

* since every architectural layer lives in its own Gradle module, during testing I realised that some testing utility classes should be visible across all Gradle modules, both for 'test' and 'androidTest' source code directories. For example, for testing coroutines, I needed a custom JUnit rule to set the main coroutines dispatcher to a TestCoroutineScope. For a single Gradle module I've created a 'sharedTest' source code directory and put the shared testing code there. But for other Gradle modules I simply copied the needed class, which is bad, I know.

## Demo


https://user-images.githubusercontent.com/37955938/126480469-ded88300-f99d-4880-adc3-625a52655a76.mp4






