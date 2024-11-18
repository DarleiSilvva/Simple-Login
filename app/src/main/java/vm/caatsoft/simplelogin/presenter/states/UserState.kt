package vm.caatsoft.simplelogin.presenter.states

import vm.caatsoft.simplelogin.domain.entities.UserResponseEntity

sealed class UserState {
    data object Idle : UserState()
    data object Loading : UserState()
    data class Success(val userResponseEntity: UserResponseEntity) : UserState()
    data class Error(val message: String) : UserState()
}