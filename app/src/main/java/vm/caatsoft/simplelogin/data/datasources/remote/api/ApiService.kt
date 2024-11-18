package vm.caatsoft.simplelogin.data.datasources.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import vm.caatsoft.simplelogin.core.Constants.LOGIN_ENDPOINT
import vm.caatsoft.simplelogin.core.Constants.USER_PROFILE_ENDPOINT
import vm.caatsoft.simplelogin.data.models.LoginResponseModel
import vm.caatsoft.simplelogin.data.models.UserResponseModel
import vm.caatsoft.simplelogin.data.models.LoginRequestModel

interface ApiService {
    @POST(LOGIN_ENDPOINT)
    suspend fun login(@Body request: LoginRequestModel): LoginResponseModel

    @GET(USER_PROFILE_ENDPOINT)
    suspend fun getUser(
        @Header("Authorization") token: String
    ): Response<UserResponseModel>
}
