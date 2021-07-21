# dogbreeds-sample

## App Architecture
The app uses clean architecture. For simplicity, it has 3 layers:
* the _domain layer_ contains the business logic of the app. It holds the business models, the abstract definitions of the repositories, some mappers, and the use cases.
* the _data layer_ provides definitions of the data sources. It holds the concrete implementations of the repositories and other data sources like networking via Retrofit in this case. 
* the _presentation layer_ contains the UI-related code and makes use of the MVVM pattern in this particular case.

## Testing
* local unit tests to test the usecases; since every use case depends on the repository, in order to test in isolation, I  have created a fake repository that implements the same interface as the real repository, and thus can be swapped during testing via dependency injection (which is managed by Hilt). 
* integration tests for view models and fragments
* to handle coroutines testing I have used the TestCoroutineDispatcher provided by the [kotlinx-coroutines-test](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/index.html) library
* to test the REST API interface defined for Retrofit I made use of [MockWebServer](https://github.com/square/okhttp/tree/master/mockwebserver)
* for UI test I used Espresso

## Problems I have encountered
* since Espresso doesn't play nicely with coroutines, data binding or Hilt, I had to add a lot of boilerplate code that was not written by me. I mostly used the Google samples as examples, and every copied class/file has links in the comments that point to the original code.
* since every architectural layer lives in its own Gradle module, during testing I realised that some testings utility classes should be visible across all Gradle modules, both for 'test' and 'androidTest' source code directories. For example, for testing coroutines, I needed a custom JUnit rule to set the main coroutines dispatcher to a TestCoroutineScope. For a single Gradle module I've created a 'sharedTest' source code directory and put the shared testing code there. But for other Gradle modules I simply copied the needed class, which is bad, I know.

## Demo