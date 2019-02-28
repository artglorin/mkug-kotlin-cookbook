package sample

fun main(args: Array<String>) {
    val arguments = args.toArguments()
    for (index in arguments.count downTo 0) {
        println("$index...")
    }
    println(arguments)
}

private fun Array<String>.toArguments() =
    Arguments(
        getOrNull(0).ifNullOrBlank { "Kotlin User Group" },
        getOrNull(1).ifNullOrBlank { "Hello, {{name}}!" },
        getOrNull(2)?.toIntOrNull() ?: 5

    )

private inline fun <C : CharSequence?, R : CharSequence> C.ifNullOrBlank(defaultValue: () -> R): R {
    @Suppress("UNCHECKED_CAST")
    return if (this.isNullOrBlank()) defaultValue() else this as R
}

data class Arguments(
    val name: String,
    val greetPattern: String,
    val count: Int
) {
    override fun toString(): String {
        return greetPattern.replace("{{name}}", name)
    }
}