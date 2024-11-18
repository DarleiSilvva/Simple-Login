package vm.caatsoft.simplelogin.presenter.view.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.getViewModel
import vm.caatsoft.simplelogin.presenter.view.theme.SimpleLoginTheme
import vm.caatsoft.simplelogin.presenter.view.ui.nav_graph.AppNavGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleLoginTheme {
                val navController = rememberNavController()
                AppNavGraph(navController = navController, viewModel = getViewModel())
            }
        }
    }
}
