package com.users.data.repository

import com.users.data.model.Coordinates
import com.users.data.model.Dob
import com.users.data.model.Id
import com.users.data.model.Info
import com.users.data.model.Location
import com.users.data.model.Login
import com.users.data.model.Name
import com.users.data.model.Picture
import com.users.data.model.Registered
import com.users.data.model.ResultData
import com.users.data.model.Street
import com.users.data.model.Timezone
import com.users.data.model.UsersData
import com.users.data.remote.UsersApi
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.test.assertFailsWith

class UsersApiTest {

    private val mockWebService = MockWebServer()

    private val client = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.SECONDS)
        .readTimeout(1, TimeUnit.SECONDS)
        .writeTimeout(1, TimeUnit.SECONDS)
        .build()

    private val api = Retrofit.Builder()
        .baseUrl(mockWebService.url("/"))
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(UsersApi::class.java)

    @After
    fun tearDown() {
        mockWebService.shutdown()
    }

    @Test
    fun `Given 200 response When fetching users Then returns users correctly`() {
        // Given
        mockWebService.enqueueResponse(
            fileName = "users.json",
            code = 200
        )
        val expected = UsersData(
            info = Info(
                seed = "lydia",
                results = 20,
                page = 1,
                version = "1.3"
            ),
            resultData = listOf(
                ResultData(
                    gender = "female",
                    name = Name(
                        title = "Mrs",
                        first = "Frances",
                        last = "Herrera"
                    ),
                    location = Location(
                        street = Street(
                            number = 5740,
                            name = "W Dallas St"
                        ),
                        city = "Wollongong",
                        state = "Australian Capital Territory",
                        country = "Australia",
                        postcode = "3018",
                        coordinates = Coordinates(
                            latitude = "-83.6031",
                            longitude = "18.6994"
                        ),
                        timezone = Timezone(
                            offset = "+2:00",
                            description = "Kaliningrad, South Africa"
                        )
                    ),
                    email = "frances.herrera@example.com",
                    login = Login(
                        uuid = "56989ef1-ee6a-4d6b-a20f-18b343213437",
                        username = "yellowbutterfly577",
                        password = "8989",
                        salt = "3zM1zTzh",
                        md5 = "b0a240ad4aea0d1a4ef28339ed246c3f",
                        sha1 = "32c3d0001a4379945544c56a8ef92f98dd6891b4",
                        sha256 =
                        "a5ed6f0b69b91ba8c444caf4b881f853fd5d36e6dfa68eddb11bebe21304b455"
                    ),
                    dob = Dob(
                        date = "1983-05-27T11:26:06.318Z",
                        age = 42
                    ),
                    registered = Registered(
                        date = "2006-01-06T13:34:20.469Z",
                        age = 19
                    ),
                    phone = "02-1122-1470",
                    cell = "0444-339-924",
                    id = Id(
                        name = "TFN",
                        value = "174195871"
                    ),
                    picture = Picture(
                        large = "https://randomuser.me/api/portraits/women/27.jpg",
                        medium = "https://randomuser.me/api/portraits/med/women/27.jpg",
                        thumbnail = "https://randomuser.me/api/portraits/thumb/women/27.jpg"
                    ),
                    nat = "AU"

                ),

                )
        )

        // When
        val actual = runBlocking { api.getUsers(1) }
        val request = mockWebService.takeRequest()

        // Then
        assertEquals(expected, actual)
        assertEquals("/?seed=lydia&results=20&page=&page=1", request.path)
    }

    @Test
    fun `Given 404 response when fetching users the throws HTTPException`() {
        mockWebService.enqueueResponse(
            fileName = "not-found.json",
            code = 404
        )
        val exception = assertFailsWith<HttpException> {
            runBlocking { api.getUsers(1) }
        }
        assertEquals(404, exception.code())
    }
}
