package vm.caatsoft.simplelogin.core

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import vm.caatsoft.simplelogin.core.di.appModule

class SimpleLogin : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@SimpleLogin)
            modules(appModule)
        }
    }
}
