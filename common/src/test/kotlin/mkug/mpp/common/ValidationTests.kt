package mkug.mpp.common

import kotlin.test.Test
import kotlin.test.assertFailsWith

@Test
fun `test when user name is blank then validation is failed`() {
    assertFailsWith<IllegalStateException> {
        UserValidator.validate(
            getUserTemplate().copy(name = "")
        )
    }
}

@Test
fun `test when user id is blank and id validation enabled then validation is failed`() {
    assertFailsWith<IllegalStateException> {
        UserValidator.validate(
            getUserTemplate().copy(
                id = ""
            ),
            validateId = true
        )
    }
}

@Test
fun `test when user age is 17 then validation is failed `() {
    assertFailsWith<IllegalStateException> {
        UserValidator.validate(
            getUserTemplate().copy(age = 17)
        )
    }
}

private fun getUserTemplate() =
    User("Name", "name", 18, Role("user"))

@Test
fun `test success validation`() {
    UserValidator.validate(
        getUserTemplate()
    )
}