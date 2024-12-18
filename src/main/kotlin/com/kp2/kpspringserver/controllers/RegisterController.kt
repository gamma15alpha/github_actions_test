package com.kp2.kpspringserver.controllers

import com.kp2.kpspringserver.common.model.RegistrationForm
import com.kp2.kpspringserver.common.model.User
import com.kp2.kpspringserver.common.repository.UserRepository
import com.kp2.kpspringserver.common.repository.UserRoleRepository
import com.kp2.kpspringserver.common.service.UserRoleService
import com.kp2.kpspringserver.common.service.UserService
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import java.util.*


@Controller
class RegisterController(
    private val userService: UserService,
    private val userRoleService: UserRoleService,
) {

    @GetMapping("/register")
    fun showRegistrationForm(model: Model): String {
        model.addAttribute("registrationForm", RegistrationForm(
            null, null, null, null, null, null))
        return "Security/register"
    }

    @PostMapping("/register")
    fun registerUser(
        @Valid registrationForm: RegistrationForm,
        bindingResult: BindingResult,
        model: Model
    ): String {
        if (bindingResult.hasErrors()) {
            return "Security/register"
        }

        try {
            val user = User(
                login = registrationForm.username,
                hashedPassword = registrationForm.password,
                name = registrationForm.firstName,
                surname = registrationForm.lastName,
                email = registrationForm.email,
                phone = registrationForm.phone,
                createdDate = Date(),
                isActive = true,
                role = userRoleService.findByName("ROLE_USER")
            )

            userService.save(user)
            return "redirect:/registration-success"
        } catch (e: Exception) {
            model.addAttribute("error", "Ошибка при регистрации! Попробуйте снова.")
            return "Security/register"
        }
    }

    @GetMapping("/registration-success")
    fun showSuccessPage(): String {
        return "Security/registrationSuccess"
    }
}