package vm.caatsoft.simplelogin.domain.repository

import kotlinx.coroutines.flow.Flow
import vm.caatsoft.simplelogin.domain.entities.LoginResponseEntity
import vm.caatsoft.simplelogin.domain.entities.UserResponseEntity

interface LoginRepository {
    fun login(email: String, password: String): Flow<Result<LoginResponseEntity>>

    fun getUser(token: String): Flow<Result<UserResponseEntity>>
}


