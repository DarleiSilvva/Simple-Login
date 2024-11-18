package vm.caatsoft.simplelogin.data.repository

import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.HttpException
import retrofit2.Response
import vm.caatsoft.simplelogin.data.datasources.remote.api.ApiService
import vm.caatsoft.simplelogin.data.models.LoginRequestModel
import vm.caatsoft.simplelogin.data.models.LoginResponseModel
import vm.caatsoft.simplelogin.data.models.UserDataModel
import vm.caatsoft.simplelogin.data.models.UserResponseModel
import vm.caatsoft.simplelogin.domain.entities.LoginResponseEntity
import vm.caatsoft.simplelogin.domain.entities.UserDataEntity
import vm.caatsoft.simplelogin.domain.entities.UserResponseEntity
import vm.caatsoft.simplelogin.domain.repository.LoginRepository
import kotlin.test.assertEquals

class LoginRepositoryImplTest {

    @Mock
    private lateinit var apiService: ApiService

    private lateinit var loginRepository: LoginRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        loginRepository = LoginRepositoryImpl(apiService)
    }

    @Test
    fun `login should emit success result when api call is successful`() = runBlocking {
        // Given: An email, password, and a simulated API response
        val email = "test@example.com"
        val password = "password123"
        val loginResponseModel = LoginResponseModel(token = "fake_token")
        val loginRequestModel = LoginRequestModel(email, password)
        `when`(apiService.login(loginRequestModel)).thenReturn(loginResponseModel)

        // When: The login method is called
        val result = loginRepository.login(email, password)

        // Then: The result should indicate success and contain the correct token
        result.collect {
            assertEquals(it.isSuccess, true)
            assertEquals(it.getOrNull(), LoginResponseEntity(token = "fake_token"))
        }
    }

    @Test
    fun `login should emit failure result when api call throws exception`() = runBlocking {
        // Given: An email, password, and a simulated exception
        val email = "test@example.com"
        val password = "password123"
        val loginRequestModel = LoginRequestModel(email, password)
        `when`(apiService.login(loginRequestModel)).thenThrow(RuntimeException("Network Error"))

        // When: The login method is called
        val result = loginRepository.login(email, password)

        // Then: The result should indicate failure
        result.collect {
            assertEquals(it.isFailure, true)
        }
    }

    @Test
    fun `getUser should emit success result when api call is successful`() = runBlocking {
        // Given: A token and a simulated API response with user data
        val token = "fake_token"
        val userResponseModel = UserResponseModel(
            data = UserDataModel(
                id = 1,
                email = "test@example.com",
                firstName = "John",
                lastName = "Doe",
                avatar = "avatar_url"
            )
        )
        val response = Response.success(userResponseModel)
        `when`(apiService.getUser(token)).thenReturn(response)

        // When: The getUser method is called
        val result = loginRepository.getUser(token)

        // Then: The result should indicate success and contain the user data
        result.collect {
            assertEquals(it.isSuccess, true)
            assertEquals(
                it.getOrNull(),
                UserResponseEntity(
                    data = UserDataEntity(
                        id = 1,
                        email = "test@example.com",
                        firstName = "John",
                        lastName = "Doe",
                        avatar = "avatar_url"
                    )
                )
            )
        }
    }

    @Test
    fun `getUser should emit failure result when api call fails`() = runBlocking {
        // Given: A token and a simulated API error response
        val token = "fake_token"
        val errorResponseBody = "".toResponseBody(null)
        `when`(apiService.getUser(token)).thenThrow(
            HttpException(Response.error<UserResponseModel>(500, errorResponseBody))
        )

        // When: The getUser method is called
        val result = loginRepository.getUser(token)

        // Then: The result should indicate failure
        result.collect {
            assert(it.isFailure)
        }
    }

    @Test
    fun `getUser should emit failure result when response body is null`() = runBlocking {
        // Given: A token and a null API response
        val token = "fake_token"
        val response = Response.success<UserResponseModel>(null)
        `when`(apiService.getUser(token)).thenReturn(response)

        // When: The getUser method is called
        val result = loginRepository.getUser(token)

        // Then: The result should indicate failure
        result.collect {
            assertEquals(it.isFailure, true)
        }
    }
}
