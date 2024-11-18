package vm.caatsoft.simplelogin.presenter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import vm.caatsoft.simplelogin.core.Constants.ERROR_MASSAGE
import vm.caatsoft.simplelogin.core.utils.SingleEvent
import vm.caatsoft.simplelogin.domain.usecases.GetUserUseCase
import vm.caatsoft.simplelogin.domain.usecases.LoginUseCase
import vm.caatsoft.simplelogin.presenter.states.LoginState
import vm.caatsoft.simplelogin.presenter.states.UserState

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {

    private val _loginState =
        MutableStateFlow<SingleEvent<LoginState>>(SingleEvent(LoginState.Idle))
    val loginState: StateFlow<SingleEvent<LoginState>> = _loginState.asStateFlow()

    private val _userState = MutableStateFlow<SingleEvent<UserState>>(SingleEvent(UserState.Idle))
    val userState: StateFlow<SingleEvent<UserState>> = _userState.asStateFlow()

    fun login(email: String, password: String) {
        loginUseCase(email, password)
            .onStart {
                _loginState.value = SingleEvent(LoginState.Loading)
            }
            .onEach { result ->
                _loginState.value = SingleEvent(
                    when {
                        result.isSuccess -> {
                            result.getOrNull()?.let {
                                LoginState.Success(it)
                            } ?: run {
                                LoginState.Error(ERROR_MASSAGE)
                            }
                        }

                        else -> {
                            LoginState.Error(result.exceptionOrNull()?.message ?: ERROR_MASSAGE)
                        }
                    }
                )
            }
            .catch { error ->
                _loginState.value = SingleEvent(LoginState.Error(error.message ?: ERROR_MASSAGE))
            }
            .launchIn(viewModelScope)
    }

    fun getUser(token: String) {
        getUserUseCase(token)
            .onStart {
                _userState.value = SingleEvent(UserState.Loading)
            }
            .onEach { result ->
                _userState.value = SingleEvent(
                    when {
                        result.isSuccess -> {
                            result.getOrNull()?.let {
                                UserState.Success(it)
                            } ?: run {
                                UserState.Error(ERROR_MASSAGE)
                            }
                        }

                        else -> {
                            UserState.Error(result.exceptionOrNull()?.message ?: ERROR_MASSAGE)
                        }
                    }
                )
            }
            .catch { error ->
                _userState.value = SingleEvent(UserState.Error(error.message ?: ERROR_MASSAGE))
            }
            .launchIn(viewModelScope)
    }
}
