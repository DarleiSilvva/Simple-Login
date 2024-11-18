package vm.caatsoft.simplelogin.data.models

import com.google.gson.annotations.SerializedName

data class UserDataModel(
    val id: Int,
    val email: String,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    val avatar: String
)