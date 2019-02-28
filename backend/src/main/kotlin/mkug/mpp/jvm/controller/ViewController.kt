package mkug.mpp.jvm.controller


import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.reactive.result.view.RedirectView

@Controller
class ViewController {

    @GetMapping("/app")
    fun getUser() = RedirectView("/resources/html/index.html")


}