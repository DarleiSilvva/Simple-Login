package vm.caatsoft.simplelogin.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import vm.caatsoft.simplelogin.data.datasources.remote.api.ApiService
import vm.caatsoft.simplelogin.data.models.LoginRequestModel
import vm.caatsoft.simplelogin.domain.entities.LoginResponseEntity
import vm.caatsoft.simplelogin.domain.entities.UserDataEntity
import vm.caatsoft.simplelogin.domain.entities.UserResponseEntity
import vm.caatsoft.simplelogin.domain.repository.LoginRepository

class LoginRepositoryImpl(private val apiService: ApiService) : LoginRepository {

    override fun login(email: String, password: String): Flow<Result<LoginResponseEntity>> = flow {
        val response = apiService.login(LoginRequestModel(email, password))
        val result = LoginResponseEntity(
            token = response.token
        )
        emit(Result.success(result))
    }.catch { exception ->
        emit(Result.failure(exception))
    }

    override fun getUser(token: String): Flow<Result<UserResponseEntity>> = flow {
        try {
            val response = apiService.getUser(token)

            if (response.isSuccessful) {
                response.body()?.let {
                    val result = UserResponseEntity(
                        UserDataEntity(
                            id = it.data.id,
                            email = it.data.email,
                            firstName = it.data.firstName,
                            lastName = it.data.lastName,
                            avatar = it.data.avatar
                        )
                    )
                    emit(Result.success(result))
                } ?: emit(Result.failure(Exception("Empty response body")))
            } else {
                emit(Result.failure(Exception("Error: ${response.code()}")))
            }
        } catch (e: HttpException) {
            emit(Result.failure(e))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }.catch { exception ->
        emit(Result.failure(exception))
    }
}
