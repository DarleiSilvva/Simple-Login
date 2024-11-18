package vm.caatsoft.simplelogin.domain.usecases

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import vm.caatsoft.simplelogin.domain.entities.LoginResponseEntity
import vm.caatsoft.simplelogin.domain.repository.LoginRepository
import kotlinx.coroutines.flow.first

class LoginUseCaseTest {

    private lateinit var loginUseCase: LoginUseCase
    private val loginRepository: LoginRepository = mockk()

    @Before
    fun setUp() {
        // Initialize the UseCase with the mocked repository
        loginUseCase = LoginUseCase(loginRepository)
    }

    @Test
    fun `should return success when repository returns login data`() = runTest {
        // Given: A valid email, password, and mocked repository response
        val email = "test@example.com"
        val password = "password123"
        val loginResponseEntity = LoginResponseEntity(token = "fakeToken")
        coEvery { loginRepository.login(email, password) } returns flow {
            emit(Result.success(loginResponseEntity))
        }

        // When: The UseCase is invoked with the given email and password
        val result: Result<LoginResponseEntity> = loginUseCase(email, password).first()

        // Then: The result should indicate success and return the correct login data
        assert(result.isSuccess)
        assert(result.getOrNull() == loginResponseEntity)

        // Verify that the repository was called with the correct parameters
        verify { loginRepository.login(email, password) }
    }

    @Test
    fun `should return error when repository fails to login`() = runTest {
        // Given: An invalid email or password and a mocked repository response with an error
        val email = "test@example.com"
        val password = "wrongPassword"
        val errorMessage = "Invalid credentials"
        coEvery { loginRepository.login(email, password) } returns flow {
            emit(Result.failure(Exception(errorMessage)))
        }

        // When: The UseCase is invoked with the given email and password
        val result: Result<LoginResponseEntity> = loginUseCase(email, password).first()

        // Then: The result should indicate failure with the correct error message
        assert(result.isFailure)
        assert(result.exceptionOrNull() is Exception)
        assert(result.exceptionOrNull()?.message == errorMessage)

        // Verify that the repository was called with the correct parameters
        verify { loginRepository.login(email, password) }
    }
}
