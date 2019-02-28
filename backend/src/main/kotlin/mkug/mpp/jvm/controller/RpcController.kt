package mkug.mpp.jvm.controller

import mkug.mpp.common.Role
import mkug.mpp.common.User
import mkug.mpp.common.UserValidator
import mkug.mpp.common.ValidationException
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import java.util.*

@RestController
@RequestMapping("rpc")
class RpcController {
    private var counter: Int = 0

    @GetMapping(value = ["/user"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getUser() = Mono.just(
        User(
            id = "${counter++}",
            age = (Random().nextInt(8) + 17),
            name = "MKUG-user",
            role = Role(if (Random().nextBoolean()) "user" else "admin")
        )
    )

    @PostMapping(
        value = ["/user"],
        consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun createUser(@RequestBody request: Mono<User>): Mono<String> {
        return request.flatMap { user ->
            Mono.create<String> { sink ->
                UserValidator.validate(user)
                if (user.age > 100) {
                    throw ValidationException("Age must be less then 100: age - ${user.age}")
                }
                val answer = "User created: $user"
                print(answer)
                sink.success("""{ "UserId": "${counter++}" }""")
            }
        }.onErrorResume {
            Mono.just("""{"Error": "${it.message?.replace("\"", "\\\"")}" }""")
        }
    }
}