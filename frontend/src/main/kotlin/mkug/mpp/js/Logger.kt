package mkug.mpp.js

import kotlin.browser.window

internal object Logger {

    fun info(message: String?) {
        message?.let {
            window.alert(it)
        }
    }
}
