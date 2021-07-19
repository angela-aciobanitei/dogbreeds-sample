package com.ang.acb.dogbreeds.data

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
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
    fun getAllBreedImages_onSuccess_responseIsNotNull() {
        runBlocking {
            mockServer.enqueueMockResponse("get_breed_images_success.json", 200)
            val response = apiService.getBreedImages("hound", 10)

            assertThat(response, notNullValue())
        }
    }

    @Test
    fun getAllBreedImages_onSuccess_responseIsNotEmpty() {
        runBlocking {
            mockServer.enqueueMockResponse("get_breed_images_success.json", 200)
            val response = apiService.getBreedImages("hound", 10)

            assertThat(response.message?.isNotEmpty(), `is`(true))
        }
    }

    @Test
    fun getAllBreedImages_onSuccess_verifyResponseSize() {
        runBlocking {
            mockServer.enqueueMockResponse("get_breed_images_success.json", 200)
            val response = apiService.getBreedImages("hound", 10)

            assertThat(response.message?.size, `is`(10))
        }
    }

    private fun MockWebServer.enqueueMockResponse(fileName: String, code: Int) {
        val inputStream = javaClass.classLoader?.getResourceAsStream("apiresponse/$fileName")
        val source = inputStream?.let {
            inputStream.source().buffer()
        }

        source?.let {
            val mockResponse = MockResponse()
                .setResponseCode(code)
                .setBody(source.readString(Charsets.UTF_8))
            enqueue(mockResponse)
        }
    }
}
