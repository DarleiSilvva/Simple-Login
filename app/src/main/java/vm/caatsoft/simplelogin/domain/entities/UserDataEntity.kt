package vm.caatsoft.simplelogin.domain.entities

data class UserDataEntity(
    val id: Int,
    val email: String,
    val firstName: String,
    val lastName: String,
    val avatar: String
)