package com.kp2.kpspringserver.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class AuthController {
    @GetMapping("/login")
    fun loginPage(): String {
        return "Security/login"
    }
}