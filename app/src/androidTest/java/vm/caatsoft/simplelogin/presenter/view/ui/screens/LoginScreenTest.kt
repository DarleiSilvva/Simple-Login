package vm.caatsoft.simplelogin.presenter.view.ui.screens

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import vm.caatsoft.simplelogin.domain.entities.LoginResponseEntity
import vm.caatsoft.simplelogin.domain.entities.UserDataEntity
import vm.caatsoft.simplelogin.domain.entities.UserResponseEntity
import vm.caatsoft.simplelogin.domain.repository.LoginRepository
import vm.caatsoft.simplelogin.domain.usecases.GetUserUseCase
import vm.caatsoft.simplelogin.domain.usecases.LoginUseCase
import vm.caatsoft.simplelogin.presenter.states.LoginState
import vm.caatsoft.simplelogin.presenter.viewmodel.LoginViewModel

@RunWith(AndroidJUnit4::class)
class LoginScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loginScreen_displaysInputFields_whenRendered() {
        // Given: Setup the test with LoginScreen and a mock ViewModel
        composeTestRule.setContent {
            LoginScreen(
                navController = rememberNavController(),
                viewModel = LoginViewModel(
                    loginUseCase = FakeLoginUseCase(),
                    getUserUseCase = FakeGetUserUseCase()
                )
            )
        }

        // Then: Assert that the input fields for "Email" and "Password" are visible
        composeTestRule.onNodeWithText("Email").assertExists()
        composeTestRule.onNodeWithText("Password").assertExists()
    }

    @Test
    fun loginScreen_navigatesToSuccessState_whenValidCredentialsAreProvided() {
        // Given: Setup LoginScreen with valid credentials and a mock ViewModel
        val loginViewModel = LoginViewModel(
            loginUseCase = FakeLoginUseCase(),
            getUserUseCase = FakeGetUserUseCase()
        )
        composeTestRule.setContent {
            LoginScreen(navController = rememberNavController(), viewModel = loginViewModel)
        }

        // When: Input valid credentials and click the "Login" button
        composeTestRule.onNodeWithText("Email").performTextInput("test@example.com")
        composeTestRule.onNodeWithText("Password").performTextInput("password123")
        composeTestRule.onNodeWithText("Login").performClick()

        // Then: Verify that the login state is "Success"
        loginViewModel.loginState.value.getContentIfNotHandled().let { state ->
            Assert.assertTrue(state is LoginState.Success)
        }
    }

    @Test
    fun loginScreen_showsErrorState_whenInvalidCredentialsAreProvided() {
        // Given: Setup LoginScreen with invalid credentials and a mock ViewModel
        val loginViewModel = LoginViewModel(
            loginUseCase = FakeLoginUseCase(),
            getUserUseCase = FakeGetUserUseCase()
        )
        composeTestRule.setContent {
            LoginScreen(navController = rememberNavController(), viewModel = loginViewModel)
        }

        // When: Input invalid (empty) credentials and click the "Login" button
        composeTestRule.onNodeWithText("Email").performTextInput("")
        composeTestRule.onNodeWithText("Password").performTextInput("")
        composeTestRule.onNodeWithText("Login").performClick()

        // Then: Verify that the login state is "Error"
        loginViewModel.loginState.value.getContentIfNotHandled().let { state ->
            Assert.assertTrue(state is LoginState.Error)
        }
    }
}

// Fakes for the tests
class FakeLoginUseCase : LoginUseCase(loginRepository = FakeLoginRepository()) {
    override fun invoke(email: String, password: String): Flow<Result<LoginResponseEntity>> {
        return flow {
            if (email == "test@example.com" && password == "password123") {
                emit(Result.success(LoginResponseEntity(token = "xxx")))
            } else {
                emit(Result.failure(Exception("Invalid credentials")))
            }
        }
    }
}

class FakeGetUserUseCase : GetUserUseCase(loginRepository = FakeLoginRepository()) {
    override fun invoke(token: String): Flow<Result<UserResponseEntity>> {
        return flow {
            if (token == "valid_token") {
                emit(
                    Result.success(
                        UserResponseEntity(
                            data = UserDataEntity(
                                id = 1,
                                firstName = "John",
                                lastName = "Doe",
                                email = "john.doe@example.com",
                                avatar = "https://reqres.in/img/faces/2-image.jpg"
                            )
                        )
                    )
                )
            } else {
                emit(Result.failure(Exception("Invalid token")))
            }
        }
    }
}

class FakeLoginRepository : LoginRepository {
    override fun login(email: String, password: String): Flow<Result<LoginResponseEntity>> {
        return flow {
            emit(Result.success(LoginResponseEntity(token = "xxx")))
        }
    }

    override fun getUser(token: String): Flow<Result<UserResponseEntity>> {
        return flow {
            emit(
                Result.success(
                    UserResponseEntity(
                        data = UserDataEntity(
                            id = 1,
                            firstName = "John",
                            lastName = "Doe",
                            email = "john.doe@example.com",
                            avatar = "https://reqres.in/img/faces/2-image.jpg"
                        )
                    )
                )
            )
        }
    }
}
