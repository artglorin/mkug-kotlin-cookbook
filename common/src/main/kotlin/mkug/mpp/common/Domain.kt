package mkug.mpp.common

data class User(
    val id: String?,
    val name: String,
    val age: Int,
    val role: Role
)

data class Role(val roleId: String? = null)