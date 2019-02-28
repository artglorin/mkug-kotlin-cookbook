package mkug.mpp.common

open class ValidationException : RuntimeException {
    constructor() : super()
    constructor(message: String?) : super(message)
    constructor(cause: Throwable?) : super(cause)
}