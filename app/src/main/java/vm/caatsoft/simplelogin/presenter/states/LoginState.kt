package vm.caatsoft.simplelogin.presenter.states

import vm.caatsoft.simplelogin.domain.entities.LoginResponseEntity

sealed class LoginState {
    data object Idle : LoginState()
    data object Loading : LoginState()
    data class Success(val loginResponseEntity: LoginResponseEntity) : LoginState()
    data class Error(val message: String) : LoginState()
}