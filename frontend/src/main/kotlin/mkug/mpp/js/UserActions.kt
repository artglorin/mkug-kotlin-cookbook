package mkug.mpp.js

import mkug.mpp.common.User
import mkug.mpp.common.UserValidator

internal object UserActions {

    fun sendUser(user: User) {
        UserValidator.validate(
            user,
            successAction = {
                RpcService.call<Any>("user", it) {
                    then { result ->
                        Logger.info(JSON.stringify(result))
                    }
                    catch { error ->
                        Logger.info(error.message)
                    }
                }
            }) {
            Logger.info(it.joinToString("\n"))
        }
    }

    fun fetchUser(successConsumer: (User?) -> Unit) {
        RpcService.call<User>("user") {
            then { user ->
                UserValidator.validate(user, true, successAction = {
                    successConsumer(it)
//                    AppLayout.addUserToTable(it)
                }) {
                    Logger.info("Rpc call exception: $it")
                    successConsumer(null)
                }
            }
            catch {
                Logger.info(it.message)
                successConsumer(null)
            }
        }
    }
}