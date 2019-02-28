package mkug.mpp.common

object UserValidator {
    fun validate(
        user: User,
        validateId: Boolean = false,
        successAction: ((User) -> Unit)? = null,
        errorsConsumer: ((List<String>) -> Unit)? = null
    ) {
        user.apply {
            var errors: MutableList<String>? = null
            if (validateId && id.isNullOrBlank()) {
                errors = errors.initIfNeedAndAdd("Id must not be null")
            }
            if (name.isBlank()) {
                errors = errors.initIfNeedAndAdd("Name must not be null")
            }

            if (age <= 18) {
                errors = errors.initIfNeedAndAdd("Age must be great than 18")
            }
            RoleValidator.validate(role) { errors = errors.initIfNeedAndAddAll(it) }
            errors
                ?.let {
                    errorsConsumer?.invoke(it) ?: throw ValidationException(it.joinToString("\n"))
                }
                ?: successAction?.invoke(user)
        }
    }
}

object RoleValidator {
    fun validate(role: Role, errorsConsumer: ((List<String>) -> Unit)? = null) {
        with(role) {
            if (roleId.isNullOrBlank()) {
                errorsConsumer?.invoke(listOf("Role id must not be null")) ?: throw ValidationException("Role id must not be null")
            }
        }
    }
}