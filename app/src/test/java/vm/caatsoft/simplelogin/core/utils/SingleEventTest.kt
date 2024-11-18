package vm.caatsoft.simplelogin.core.utils

import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import vm.caatsoft.simplelogin.domain.entities.LoginResponseEntity
import vm.caatsoft.simplelogin.presenter.states.LoginState

class SingleEventTest {

    @Test
    fun testSingleEvent_getContentIfNotHandled() {
        // Given: A mocked SingleEvent with LoginState.Success as its content
        val mockEvent = mockk<SingleEvent<LoginState>>(relaxed = true)

        // When: getContentIfNotHandled is called, simulate it returning a login success state
        every { mockEvent.getContentIfNotHandled() } returns LoginState.Success(LoginResponseEntity(token = "xxx"))

        // Then: The result should match the expected login success state
        val result = mockEvent.getContentIfNotHandled()
        assert(result is LoginState.Success)
    }

    @Test
    fun testSingleEvent_peekContent() {
        // Given: A mocked SingleEvent with LoginState.Error as its content
        val mockEvent = mockk<SingleEvent<LoginState>>(relaxed = true)

        // When: peekContent is called, simulate it returning a login error state
        every { mockEvent.peekContent() } returns LoginState.Error("Erro de login")

        // Then: The result should match the expected login error state
        val result = mockEvent.peekContent()
        assert(result is LoginState.Error)
    }
}
