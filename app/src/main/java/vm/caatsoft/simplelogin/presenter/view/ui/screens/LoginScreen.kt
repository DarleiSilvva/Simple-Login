package vm.caatsoft.simplelogin.presenter.view.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.gson.Gson
import vm.caatsoft.simplelogin.core.Constants.DEFAULT_NO_VALUE_STRING
import vm.caatsoft.simplelogin.core.Constants.NAV_USER_PROFILE_SCREEN
import vm.caatsoft.simplelogin.presenter.states.LoginState
import vm.caatsoft.simplelogin.presenter.states.UserState
import vm.caatsoft.simplelogin.presenter.viewmodel.LoginViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun LoginScreen(navController: NavHostController, viewModel: LoginViewModel) {
    val loginStateEvent = viewModel.loginState.collectAsState().value
    val loginState = loginStateEvent.getContentIfNotHandled()

    val userStateEvent = viewModel.userState.collectAsState().value
    val userState = userStateEvent.getContentIfNotHandled()

    var email by remember { mutableStateOf(DEFAULT_NO_VALUE_STRING) }
    var password by remember { mutableStateOf(DEFAULT_NO_VALUE_STRING) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                viewModel.login(email, password)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }
        Spacer(modifier = Modifier.height(16.dp))

        when (loginState) {
            is LoginState.Idle -> Text("Enter your credentials to login.")
            is LoginState.Loading -> CircularProgressIndicator(modifier = Modifier.size(48.dp))
            is LoginState.Success -> {
                val token = loginState.loginResponseEntity.token
                viewModel.getUser(token)
            }

            is LoginState.Error -> Text("Error: ${loginState.message}")
            else -> {}
        }

        when (userState) {
            is UserState.Idle -> {}
            is UserState.Loading -> CircularProgressIndicator(modifier = Modifier.size(48.dp))
            is UserState.Success -> {
                val userData = userState.userResponseEntity.data
                val gson = Gson()
                val userDataJson = gson.toJson(userData)
                val encodedUserDataJson =
                    URLEncoder.encode(userDataJson, StandardCharsets.UTF_8.toString())
                navController.navigate("$NAV_USER_PROFILE_SCREEN/$encodedUserDataJson")
            }

            is UserState.Error -> Text("Error: ${userState.message}")
            else -> {}
        }
    }
}
