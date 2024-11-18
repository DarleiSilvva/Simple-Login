package vm.caatsoft.simplelogin.presenter.view.ui.screens

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import vm.caatsoft.simplelogin.domain.entities.UserDataEntity
import vm.caatsoft.simplelogin.presenter.view.ui.screens.UserProfileScreen as UserProfileScreen1

@RunWith(AndroidJUnit4::class)
class UserProfileScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun userProfileScreen_displaysUserData_whenUserIsProvided() {
        // Given: A user data entity is provided
        val user = UserDataEntity(
            id = 1,
            firstName = "John",
            lastName = "Doe",
            email = "john.doe@example.com",
            avatar = "https://reqres.in/img/faces/2-image.jpg"
        )

        // When: The UserProfileScreen is rendered
        composeTestRule.setContent {
            UserProfileScreen1(userDataEntity = user)
        }

        // Then: The user's name and email should be displayed
        composeTestRule.onNodeWithText("NAME: John Doe").assertExists()
        composeTestRule.onNodeWithText("EMAIL: john.doe@example.com").assertExists()
        composeTestRule.onNodeWithContentDescription("User Avatar").assertExists()
    }
}
