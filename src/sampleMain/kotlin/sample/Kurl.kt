package sample

import sample.curl.CUrl

fun curl(args: Array<String>) {
    if (args.isEmpty())
        return help()
    println("""
        |--------------------------------------
        |Get page: ${args[0]}
        |--------------------------------------
    """.trimMargin())

    val curl = CUrl(args[0])
    curl.header += {
        println("[H] $it")
    }

    curl.body += {
        println("[B] $it")
    }

    curl.fetch()
    curl.close()
}

fun help() {
    println("ERROR: missing URL command line argument")
}