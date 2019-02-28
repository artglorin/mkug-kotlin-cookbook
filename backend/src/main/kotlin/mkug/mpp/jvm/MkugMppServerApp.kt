package mkug.mpp.jvm

import mkug.mpp.jvm.controller.RpcController
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(
    scanBasePackageClasses = [RpcController::class]
)
class KotlinMppApplication

fun main(args: Array<String>) {
    runApplication<KotlinMppApplication>(*args)
}
