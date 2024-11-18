package vm.caatsoft.simplelogin.domain.usecases

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import vm.caatsoft.simplelogin.domain.entities.UserResponseEntity
import vm.caatsoft.simplelogin.domain.repository.LoginRepository
import kotlinx.coroutines.flow.first
import vm.caatsoft.simplelogin.domain.entities.UserDataEntity

class GetUserUseCaseTest {

    private lateinit var getUserUseCase: GetUserUseCase
    private val loginRepository: LoginRepository = mockk()

    @Before
    fun setUp() {
        // Initialize the UseCase with the mocked repository
        getUserUseCase = GetUserUseCase(loginRepository)
    }

    @Test
    fun `should return success when repository returns user data`() = runTest {
        // Given: A token and a mocked repository response with user data
        val token = "testToken"
        val userResponseEntity = UserResponseEntity(
            data = UserDataEntity(
                id = 1,
                firstName = "John",
                lastName = "Doe",
                email = "john.doe@example.com",
                avatar = "https://reqres.in/img/faces/2-image.jpg"
            )
        )
        coEvery { loginRepository.getUser(token) } returns flow {
            emit(Result.success(userResponseEntity))
        }

        // When: The UseCase is invoked with the token
        val result: Result<UserResponseEntity> = getUserUseCase(token).first()

        // Then: The result should indicate success and return the correct user data
        assert(result.isSuccess)
        assert(result.getOrNull() == userResponseEntity)

        // Verify that the repository was called with the correct token
        verify { loginRepository.getUser(token) }
    }

    @Test
    fun `should return error when repository fails to fetch user data`() = runTest {
        // Given: A token and a mocked repository response with an error
        val token = "testToken"
        val errorMessage = "User not found"
        coEvery { loginRepository.getUser(token) } returns flow {
            emit(Result.failure(Exception(errorMessage)))
        }

        // When: The UseCase is invoked with the token
        val result: Result<UserResponseEntity> = getUserUseCase(token).first()

        // Then: The result should indicate failure with the correct error message
        assert(result.isFailure)
        assert(result.exceptionOrNull() is Exception)
        assert(result.exceptionOrNull()?.message == errorMessage)

        // Verify that the repository was called with the correct token
        verify { loginRepository.getUser(token) }
    }
}
