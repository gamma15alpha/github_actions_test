package com.kp2.kpspringserver.controllers


import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class MainController {

    @GetMapping("/index")
    fun homePage(): String {
        return "index"
    }
}
