package vm.caatsoft.simplelogin.core.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import vm.caatsoft.simplelogin.core.Constants.BASE_URL
import vm.caatsoft.simplelogin.data.datasources.remote.api.ApiService
import vm.caatsoft.simplelogin.data.repository.LoginRepositoryImpl
import vm.caatsoft.simplelogin.domain.repository.LoginRepository
import vm.caatsoft.simplelogin.domain.usecases.GetUserUseCase
import vm.caatsoft.simplelogin.domain.usecases.LoginUseCase
import vm.caatsoft.simplelogin.presenter.viewmodel.LoginViewModel

val appModule = module {

    single<ApiService> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    // Repository
    single<LoginRepository> { LoginRepositoryImpl(get()) }

    // Use Cases
    factory { LoginUseCase(get()) }
    factory { GetUserUseCase(get()) }

    // ViewModel
    viewModel { LoginViewModel(get(), get()) }
}
