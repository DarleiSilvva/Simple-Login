package vm.caatsoft.simplelogin.presenter.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import vm.caatsoft.simplelogin.domain.entities.LoginResponseEntity
import vm.caatsoft.simplelogin.domain.entities.UserDataEntity
import vm.caatsoft.simplelogin.domain.entities.UserResponseEntity
import vm.caatsoft.simplelogin.domain.usecases.GetUserUseCase
import vm.caatsoft.simplelogin.domain.usecases.LoginUseCase
import vm.caatsoft.simplelogin.presenter.states.LoginState
import vm.caatsoft.simplelogin.presenter.states.UserState

@ExperimentalCoroutinesApi
class LoginViewModelTest {

    private lateinit var loginViewModel: LoginViewModel
    private val loginUseCase: LoginUseCase = mockk()
    private val getUserUseCase: GetUserUseCase = mockk()

    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        // Given: Set the main dispatcher to the test dispatcher
        kotlinx.coroutines.Dispatchers.setMain(testDispatcher)

        // Given: Initialize the ViewModel with mock use cases
        loginViewModel = LoginViewModel(loginUseCase, getUserUseCase)
    }

    @Test
    fun `given valid credentials, when login is called, then success state is emitted`() = runTest {
        // Given: Mock successful login response
        val mockLoginResponse = LoginResponseEntity("mock_token")
        coEvery { loginUseCase.invoke("test@example.com", "password123") } returns flow {
            emit(Result.success(mockLoginResponse))
        }

        // When: Login is called with valid credentials
        loginViewModel.login("test@example.com", "password123")

        // Then: Verify success state and response token
        advanceUntilIdle()

        val state = loginViewModel.loginState.value.getContentIfNotHandled()
        assertTrue(state is LoginState.Success)

        state?.let {
            val successState = it as LoginState.Success
            assertEquals("mock_token", successState.loginResponseEntity.token)
        }

        // Then: Verify if the use case was called with correct parameters
        coVerify { loginUseCase.invoke("test@example.com", "password123") }
    }

    @Test
    fun `given invalid credentials, when login is called, then error state is emitted`() = runTest {
        // Given: Mock failed login response
        val errorMessage = "Login failed"
        coEvery { loginUseCase.invoke("test@example.com", "wrongpassword") } returns flow {
            emit(Result.failure(Exception(errorMessage)))
        }

        // When: Login is called with invalid credentials
        loginViewModel.login("test@example.com", "wrongpassword")

        // Then: Verify error state with the expected error message
        advanceUntilIdle()

        val state = loginViewModel.loginState.value.getContentIfNotHandled()
        assertTrue(state is LoginState.Error)

        state?.let {
            val errorState = it as LoginState.Error
            assertEquals(errorMessage, errorState.message)
        }
    }

    @Test
    fun `given valid token, when getUser is called, then success state with user data is emitted`() = runTest {
        // Given: Mock successful user data response
        val mockUserResponse = UserResponseEntity(UserDataEntity(1, "test@example.com", "John", "Doe", "avatar_url"))
        every { getUserUseCase.invoke("mock_token") } returns flowOf(Result.success(mockUserResponse))

        // When: Get user is called with a valid token
        loginViewModel.getUser("mock_token")

        // Then: Verify success state and user email
        advanceUntilIdle()

        val state = loginViewModel.userState.value.getContentIfNotHandled()
        assertTrue(state is UserState.Success)

        state?.let {
            if (it is UserState.Success) {
                assertEquals("test@example.com", it.userResponseEntity.data.email)
            }
        }

        // Then: Verify if the use case was called with the correct token
        coVerify { getUserUseCase.invoke("mock_token") }
    }

    @Test
    fun `given invalid token, when getUser is called, then error state is emitted`() = runTest {
        // Given: Mock failed user data response due to invalid token
        val errorMessage = "User not found"
        coEvery { getUserUseCase.invoke("invalid_token") } returns flow {
            emit(Result.failure(Exception(errorMessage)))
        }

        // When: Get user is called with an invalid token
        loginViewModel.getUser("invalid_token")

        // Then: Verify error state with the expected error message
        advanceUntilIdle()

        val state = loginViewModel.userState.value.getContentIfNotHandled()
        assertTrue(state is UserState.Error)

        state?.let {
            val errorState = it as UserState.Error
            assertEquals(errorMessage, errorState.message)
        }
    }
}
