package com.kp2.kpspringserver.controllers.api

import com.kp2.kpspringserver.common.model.User
import com.kp2.kpspringserver.common.model.UserRole
import com.kp2.kpspringserver.common.service.UserService
import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity
import org.springframework.beans.factory.annotation.Autowired

@RestController
@RequestMapping("/api/users")
class UserController @Autowired constructor(private val userService: UserService) {

    @GetMapping("/search")
    fun searchUsers(
        @RequestParam(required = false) username: String?,
        @RequestParam(required = false) isActive: Boolean?
    ): ResponseEntity<List<User>> {
        val users = userService.search(username, isActive)
        return ResponseEntity.ok(users)
    }

    @GetMapping("/filter")
    fun filterAndSort(
        @RequestParam isActive: Boolean,
        @RequestParam role: UserRole
    ): ResponseEntity<List<User>> {
        val users = userService.filterAndSort(isActive, role)
        return ResponseEntity.ok(users)
    }

    @GetMapping
    fun getAllUsers(@RequestParam isActive: Boolean): ResponseEntity<List<User>> {
        val users = userService.getAll(isActive)
        return ResponseEntity.ok(users)
    }

    @GetMapping("/login/{login}")
    fun findByLogin(@PathVariable login: String): ResponseEntity<User> {
        val user = userService.findByLogin(login)
        return if (user != null) {
            ResponseEntity.ok(user)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): ResponseEntity<User> {
        val user = userService.getById(id)
        return if (user != null) {
            ResponseEntity.ok(user)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping
    fun createUser(@RequestBody user: User): ResponseEntity<User> {
        val savedUser = userService.save(user)
        return ResponseEntity.ok(savedUser)
    }

    @PostMapping("/deactivate")
    fun deactivateUsers(@RequestBody ids: List<Long>): ResponseEntity<Void> {
        userService.deactivate(ids)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/activate")
    fun activateUsers(@RequestBody ids: List<Long>): ResponseEntity<Void> {
        userService.activate(ids)
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping
    fun deleteUsers(@RequestBody ids: List<Long>): ResponseEntity<Void> {
        userService.delete(ids)
        return ResponseEntity.noContent().build()
    }
}
