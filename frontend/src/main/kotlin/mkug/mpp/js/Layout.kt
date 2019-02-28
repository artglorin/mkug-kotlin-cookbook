package mkug.mpp.js

import kotlinx.html.*
import kotlinx.html.dom.append
import kotlinx.html.js.div
import kotlinx.html.js.onClickFunction
import kotlinx.html.js.table
import mkug.mpp.common.User
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import kotlin.browser.document

internal class Clock(anchor: HTMLElement) {
    init {
        anchor.append {
            div {
                canvas {
                    id = "clock"
                    width = "120"
                    height = "120"
                    style = """
                        border-radius: 120px;
                    """.trimIndent()
                }
            }
        }
        clock_conti(120, getCanvasContext("clock"), ClockOptions())
    }
}

internal class AppLayout(anchor: HTMLElement, init: AppLayout.() -> Unit) {
    var fetchUserAction: (Event) -> Unit = {}
    val clock: Clock
    val userInputForm: UserInputForm
    val userTable: UserTable

    init {
        clock = Clock(anchor)
        userInputForm = UserInputForm(anchor)
        anchor.fetchButton()
        userTable = UserTable(anchor)
        init()
    }

    private fun HTMLElement.fetchButton() {
        append {
            span {
                style = """
                    margin : 1em 0.5em;
                    padding: 0.2em 0.5em;
                    display: inline-block;
                    background: #558631;
                    color: white;
                    border-radius: 0.5em;
                    font-family: sans-serif;
                    border-width: 10px;
                    border-color: white;
                    box-shadow: 1px 1px 4px 1px #74729c;
                    """
                onClickFunction = { fetchUserAction(it) }
                +"Fetch user"
            }
        }
    }


    internal class UserTable constructor(anchor: HTMLElement) {
        private val tableId = "userTable"

        init {
            anchor.append { createUserTable() }
        }

        fun addUserToTable(user: User) {
            document.getElementById(tableId)
                ?.append {
                    tr {
                        td {
                            +user.id!!
                        }
                        td {
                            +user.name
                        }
                        td {
                            +user.age.toString()
                        }
                        td {
                            +user.role.roleId!!
                        }
                    }
                }
        }

        private fun TagConsumer<HTMLElement>.createUserTable() {
            table {
                id = tableId
                style = """ margin-top: 1em; """
                tr {
                    th {
                        +"id"
                    }
                    th {
                        +"name"
                    }
                    th {
                        +"age"
                    }
                    th {
                        +"role"
                    }
                }
            }
        }
    }

    internal class UserInputForm(anchor: HTMLElement) {
        var createAction: Event.() -> Unit = {}

        private val ageInputId = "ageInput"
        private val roleInputId = "roleInput"
        private val nameInputId = "nameInput"

        private object UserFormStyles {

            const val labelStyle = """
                        margin : 1em 0.5em;;
                        display: block;
                    """
            const val form = """
                        margin-top: 1.0em;
                    """
            const val createUserButton: String = """
                                padding: 0.2em 0.5em;
                                margin-left: 0.5em;
                                background: #198acc;
                                color: white;
                                border-radius: 0.5em;
                                font-family: sans-serif;
                                border-width: 10px;
                                border-color: white;
                                box-shadow: 1px 1px 4px 1px #74729c;
                            """
            const val clearFormButton: String = """
                                padding: 0.2em 0.5em;
                                margin-left: 0.5em;
                                background: #FF5722;
                                color: white;
                                border-radius: 0.5em;
                                font-family: sans-serif;
                                border-width: 10px;
                                border-color: white;
                                box-shadow: 1px 1px 4px 1px #74729c;
                            """
            const val inputStyle: String = "margin-left: 0.5em;"

            const val buttonBlock: String = """
                        margin-top: 1.0em;
                    """

        }

        init {
            anchor.append {
                createUserInputForm()
                ageInput = 0
            }
        }

        var nameInput: String
            get() = (document.getElementById(nameInputId) as? HTMLInputElement)?.value ?: ""
            set(value) {
                (document.getElementById(nameInputId) as? HTMLInputElement)?.value = value
            }
        var ageInput: Int
            get() = (document.getElementById(ageInputId) as? HTMLInputElement)?.value?.toIntOrNull() ?: -1
            set(value) {
                (document.getElementById(ageInputId) as? HTMLInputElement)?.value = value.toString()
            }

        var roleInput: String
            get() = (document.getElementById(roleInputId) as? HTMLInputElement)?.value ?: ""
            set(value) {
                (document.getElementById(roleInputId) as? HTMLInputElement)?.value = value
            }


        private fun TagConsumer<HTMLElement>.createUserInputForm() {
            div {
                style = UserFormStyles.form
                label {
                    style = UserFormStyles.labelStyle
                    +"name"
                    input {
                        style = UserFormStyles.inputStyle
                        id = nameInputId
                        type = InputType.text
                    }
                }
                label {
                    style = UserFormStyles.labelStyle
                    +"age"
                    input {
                        style = UserFormStyles.inputStyle
                        type = InputType.number
                        id = ageInputId
                    }
                }
                label {
                    style = UserFormStyles.labelStyle
                    +"role"
                    input {
                        style = UserFormStyles.inputStyle
                        type = InputType.text
                        id = roleInputId
                    }
                }
                div {
                    style = UserFormStyles.buttonBlock
                    span {
                        style = UserFormStyles.clearFormButton
                        +"Clear form"
                        onClickFunction = {
                            nameInput = ""
                            ageInput = 0
                            roleInput = ""
                        }
                    }
                    span {
                        style = UserFormStyles.createUserButton
                        +"Create user"
                        onClickFunction = {
                            createAction(it)
                        }
                    }
                }
            }
        }
    }
}




