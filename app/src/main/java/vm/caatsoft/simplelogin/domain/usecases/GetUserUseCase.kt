package vm.caatsoft.simplelogin.domain.usecases

import kotlinx.coroutines.flow.Flow
import vm.caatsoft.simplelogin.domain.entities.UserResponseEntity
import vm.caatsoft.simplelogin.domain.repository.LoginRepository

open class GetUserUseCase(
    private val loginRepository: LoginRepository
) {
    open operator fun invoke(token: String): Flow<Result<UserResponseEntity>> {
        return loginRepository.getUser(token)
    }
}
