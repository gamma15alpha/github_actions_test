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
import java.sql.Date
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
		if (userRoleService.findByName("ROLE_ADMIN") == null) {
			userRoleService.save(
				UserRole(
					id = null,
					name = "ROLE_ADMIN",
					status = 1,
					isDeleted = false
			))
		}
		if (userRoleService.findByName("ROLE_MODERATOR") == null) {
			userRoleService.save(
				UserRole(
					id = null,
					name = "ROLE_MODERATOR",
					status = 2,
					isDeleted = false
				))
		}
		if (userRoleService.findByName("ROLE_LECTOR") == null) {
			userRoleService.save(
				UserRole(
					id = null,
					name = "ROLE_LECTOR",
					status = 3,
					isDeleted = false
				))
		}
		if (userRoleService.findByName("ROLE_STUDENT") == null) {
			userRoleService.save(
				UserRole(
					id = null,
					name = "ROLE_STUDENT",
					status = 4,
					isDeleted = false
				))
		}
		if (userRoleService.findByName("ROLE_USER") == null) {
			userRoleService.save(
				UserRole(
					id = null,
					name = "ROLE_USER",
					status = 5,
					isDeleted = false
				))
		}

		if(userService.findByLogin("defaultAdmin") == null){
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
}