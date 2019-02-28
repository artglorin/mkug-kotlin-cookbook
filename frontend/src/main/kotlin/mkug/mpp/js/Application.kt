package mkug.mpp.js

import mkug.mpp.common.Role
import mkug.mpp.common.User
import kotlin.browser.document

class Application {
    init {
        document.body!!.removeChild(document.getElementById("backend-layout")!!)
        AppLayout(document.body!!) {
            val layout = this
            fetchUserAction = {
                UserActions.fetchUser {
                    it?.also { user ->
                        layout.userTable.addUserToTable(user)
                    }
                }
            }
            userInputForm.createAction = {
                UserActions.sendUser(
                    User(
                        id = null,
                        name = layout.userInputForm.nameInput,
                        age = layout.userInputForm.ageInput,
                        role = Role(layout.userInputForm.roleInput)
                    )
                )
            }
        }
    }

}


