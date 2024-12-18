package com.kp2.kpspringserver

import com.kp2.kpspringserver.common.model.User
import com.kp2.kpspringserver.common.model.UserRole
import com.kp2.kpspringserver.common.service.UserRoleService
import com.kp2.kpspringserver.common.service.UserService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.*

@SpringBootApplication
class KpSpringServerApplication

fun main(args: Array<String>) {
	runApplication<KpSpringServerApplication>(*args)
}

@Component
class DataInitializer(
	private val userService: UserService,
	private val userRoleService: UserRoleService
) : CommandLineRunner {

	@Transactional
	override fun run(vararg args: String?) {
		initializeRoles()
		initializeDefaultAdminUser()
	}

	private fun initializeRoles() {
		val roles = listOf(
			"ROLE_ADMIN" to 1,
			"ROLE_MODERATOR" to 2,
			"ROLE_LECTOR" to 3,
			"ROLE_STUDENT" to 4,
			"ROLE_USER" to 5
		)

		roles.forEach { (roleName, status) ->
			if (userRoleService.findByName(roleName) == null) {
				saveUserRole(roleName, status)
			}
		}
	}

	private fun saveUserRole(roleName: String, status: Int) {
		userRoleService.save(
			UserRole(
				id = null,
				name = roleName,
				status = status,
				isDeleted = false
			)
		)
	}

	private fun initializeDefaultAdminUser() {
		if (userService.findByLogin("defaultAdmin") == null) {
			saveDefaultAdminUser()
		}
	}

	private fun saveDefaultAdminUser() {
		userService.save(User(
			login = "defaultAdmin",
			hashedPassword = "defaultAdmin",
			name = "defaultAdmin",
			surname = "defaultAdmin",
			email = "defaultAdmin@gmail.com",
			phone = "defaultphone",
			createdDate = Date(),
			isActive = true,
			role = userRoleService.findByName("ROLE_ADMIN")
		))
	}
}
