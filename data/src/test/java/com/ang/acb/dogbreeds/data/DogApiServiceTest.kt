package com.ang.acb.dogbreeds.data

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@RunWith(JUnit4::class)
@ExperimentalCoroutinesApi
class DogApiServiceTest {

    // Subject under test
    private lateinit var apiService: DogApiService

    private lateinit var mockServer: MockWebServer

    @Before
    fun createService() {
        mockServer = MockWebServer()

        apiService = Retrofit.Builder()
            .baseUrl(mockServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DogApiService::class.java)
    }

    @After
    fun stopService() {
        mockServer.shutdown()
    }

    @Test
    fun getAllBreeds_verifyEndpoint() {
        runBlocking {
            mockServer.enqueueMockResponse("get_all_breeds_success.json", 200)
            apiService.getAllBreeds()

            val request = mockServer.takeRequest(2000L, TimeUnit.MILLISECONDS)

            assertThat(request, notNullValue())
            assertThat(request?.path, `is`("/breeds/list/all"))
        }
    }

    @Test
    fun getAllBreeds_onSuccess_responseIsNotNull() {
        runBlocking {
            mockServer.enqueueMockResponse("get_all_breeds_success.json", 200)
            val response = apiService.getAllBreeds()

            assertThat(response, notNullValue())
        }
    }

    @Test
    fun getAllBreeds_onSuccess_responseIsNotEmpty() {
        runBlocking {
            mockServer.enqueueMockResponse("get_all_breeds_success.json", 200)
            val response = apiService.getAllBreeds()

            assertThat(response.message?.isNotEmpty(), `is`(true))
        }
    }

    @Test
    fun getAllBreeds_onSuccess_verifyResponseSize() {
        runBlocking {
            mockServer.enqueueMockResponse("get_all_breeds_success.json", 200)
            val response = apiService.getAllBreeds()

            assertThat(response.message?.size, `is`(95))
        }
    }

    @Test
    fun getBreedsImages_verifyEndpoint() {
        runBlocking {
            mockServer.enqueueMockResponse("get_breed_images_success.json", 200)
            val testBreed = "hound"
            val testCount = 10
            apiService.getBreedImages(testBreed, testCount)

            val request = mockServer.takeRequest(2000L, TimeUnit.MILLISECONDS)

            assertThat(request, notNullValue())
            assertThat(request?.path, `is`("/breed/$testBreed/images/random/$testCount"))
        }
    }

    @Test
    fun getBreedImages_onSuccess_responseIsNotNull() {
        runBlocking {
            mockServer.enqueueMockResponse("get_breed_images_success.json", 200)
            val response = apiService.getBreedImages("hound", 10)

            assertThat(response, notNullValue())
        }
    }

    @Test
    fun getBreedImages_onSuccess_responseIsNotEmpty() {
        runBlocking {
            mockServer.enqueueMockResponse("get_breed_images_success.json", 200)
            val response = apiService.getBreedImages("hound", 10)

            assertThat(response.message?.isNotEmpty(), `is`(true))
        }
    }

    @Test
    fun getBreedImages_onSuccess_verifyResponseSize() {
        runBlocking {
            mockServer.enqueueMockResponse("get_breed_images_success.json", 200)
            val response = apiService.getBreedImages("hound", 10)

            assertThat(response.message?.size, `is`(10))
        }
    }

    private fun MockWebServer.enqueueMockResponse(fileName: String, code: Int) {
        FileReader.readStringFromFile(fileName)?.let {
            val mockResponse = MockResponse()
                .setResponseCode(code)
                .setBody(it)
            enqueue(mockResponse)
        }
    }
}
