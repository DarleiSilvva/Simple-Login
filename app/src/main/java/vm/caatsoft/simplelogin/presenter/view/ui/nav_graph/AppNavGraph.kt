package vm.caatsoft.simplelogin.presenter.view.ui.nav_graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.gson.Gson
import vm.caatsoft.simplelogin.core.Constants.DEFAULT_NO_VALUE_STRING
import vm.caatsoft.simplelogin.core.Constants.NAV_ARG_USER_PROFILE
import vm.caatsoft.simplelogin.core.Constants.NAV_LOGIN_SCREEN
import vm.caatsoft.simplelogin.core.Constants.NAV_USER_PROFILE_SCREEN
import vm.caatsoft.simplelogin.domain.entities.UserDataEntity
import vm.caatsoft.simplelogin.presenter.view.ui.screens.LoginScreen
import vm.caatsoft.simplelogin.presenter.view.ui.screens.UserProfileScreen
import vm.caatsoft.simplelogin.presenter.viewmodel.LoginViewModel
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun AppNavGraph(
    navController: NavHostController,
    viewModel: LoginViewModel
) {
    NavHost(
        navController = navController,
        startDestination = NAV_LOGIN_SCREEN
    ) {
        composable(NAV_LOGIN_SCREEN) {
            LoginScreen(navController = navController, viewModel = viewModel)
        }
        composable(
            "$NAV_USER_PROFILE_SCREEN/{$NAV_ARG_USER_PROFILE}",
            arguments = listOf(
                navArgument(NAV_ARG_USER_PROFILE) { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val encodedUserDataJson =
                backStackEntry.arguments?.getString(NAV_ARG_USER_PROFILE) ?: DEFAULT_NO_VALUE_STRING
            val decodedUserDataJson =
                URLDecoder.decode(encodedUserDataJson, StandardCharsets.UTF_8.toString())
            val gson = Gson()
            val userDataEntity = gson.fromJson(decodedUserDataJson, UserDataEntity::class.java)
            UserProfileScreen(userDataEntity = userDataEntity)
        }

    }
}
