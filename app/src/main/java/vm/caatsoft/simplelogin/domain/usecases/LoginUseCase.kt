package vm.caatsoft.simplelogin.domain.usecases

import kotlinx.coroutines.flow.Flow
import vm.caatsoft.simplelogin.domain.entities.LoginResponseEntity
import vm.caatsoft.simplelogin.domain.repository.LoginRepository

open class LoginUseCase(
    private val loginRepository: LoginRepository
) {
    open operator fun invoke(email: String, password: String): Flow<Result<LoginResponseEntity>> {
        return loginRepository.login(email, password)
    }
}
