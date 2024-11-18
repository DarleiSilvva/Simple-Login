package vm.caatsoft.simplelogin.presenter.view.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import vm.caatsoft.simplelogin.domain.entities.UserDataEntity

@Composable
fun UserProfileScreen(userDataEntity: UserDataEntity) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "USER INFORMATION",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(16.dp))
        AsyncImage(
            model = userDataEntity.avatar,
            contentDescription = "User Avatar",
            modifier = Modifier.size(150.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "NAME: ${userDataEntity.firstName} ${userDataEntity.lastName}",
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = "EMAIL: ${userDataEntity.email}",
            style = MaterialTheme.typography.titleMedium
        )
    }
}
